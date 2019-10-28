package com.mqc.ioc.run;

import com.mqc.ioc.annotation.Autowired;
import com.mqc.ioc.annotation.Component;
import com.mqc.ioc.annotation.IocStarter;
import com.mqc.ioc.annotation.PackageScan;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Administrator
 */
public class IocUtil {
    private static Map<String, Object> beanContainer = new ConcurrentHashMap<>();

    private static List<Object> commandList = new ArrayList<>();

    public static void run(Class clzz) {
        //得到启动类注解
        Annotation[] annotations = clzz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof PackageScan) {
                handlePackageScan((PackageScan) annotation);
            }
        }

        for (Object command : commandList) {
            Class commandClass = command.getClass();
            try {
                Method excuteCommand = commandClass.getDeclaredMethod("command");
                excuteCommand.invoke(command);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    private static void handlePackageScan(PackageScan annotation) {
        //得到包路径 加载其中的所有类
        String packageName = annotation.value();
        Set<Class<?>> classSet = getClasses(packageName);
        for (Class<?> clzz : classSet) {
            if (clzz.isAnnotation() || clzz.isInterface()) {
                continue;
            }
            if (clzz.getAnnotation(Component.class) != null || clzz.getAnnotation(IocStarter.class) != null) {
                buildBean(clzz);
            }
        }
    }

    private static Object buildBean(Class<?> clzz){
        String className = clzz.getTypeName();
        final Object targetBean = beanContainer.get(className);
        if (targetBean != null) {
            return targetBean;
        }

        Object object = null;
        try {
            object = clzz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] fields = clzz.getDeclaredFields();
        for (Field field : fields) {
            Class fieldClass = field.getType();
            if (field.getAnnotation(Autowired.class) != null) {
                field.setAccessible(true);
                try {
                    field.set(object, buildBean(fieldClass));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        excuteInitialize(clzz, object);
        beanContainer.put(className, object);

        return object;
    }

    private static void excuteInitialize(Class<?> clzz, Object object){
        Class[] interfaceArr = clzz.getInterfaces();
        for (Class interfaceClass : interfaceArr) {
            if (interfaceClass.getName().equals("com.mqc.ioc.factory.InitializingBean")) {
                try {
                    Method init = interfaceClass.getDeclaredMethod("afterPropertiesSet");
                    init.invoke(object);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            if (interfaceClass.getName().equals("com.mqc.ioc.command.Command")) {
                commandList.add(object);
            }
        }
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClasses(String packageName){
        Set<Class<?>> classes = new LinkedHashSet<>();
        String packageDirName = packageName.replace('.', '/');
        final boolean recursive = true;
        Enumeration<URL> dirs = null;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 循环迭代下去
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            // 得到协议的名称
            String protocol = url.getProtocol();
            // 如果是以文件的形式保存在服务器上
            if ("file".equals(protocol)) {
                String filePath = null;
                try {
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // 以文件的方式扫描整个包下的文件 并添加到集合中
                findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
            } else if ("jar".equals(protocol)) {
                // 如果是jar包文件 定义一个JarFile
                findAndAddClassesInJar(classes, packageDirName, recursive, url);
            }
        }

        return classes;
    }

    private static void findAndAddClassesInJar(Set<Class<?>> classes, String packageDirName, boolean recursive, URL url) {
        JarFile jar = null;
        try {
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entries = jar.entries();
        // 同样的进行循环迭代
        while (entries.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }
            // 如果前半部分和定义的包名相同
            if (!name.startsWith(packageDirName)) {
                continue;
            }
            int idx = name.lastIndexOf('/');

            // 如果可以迭代下去 并且是一个包
            if ((idx == -1) && !recursive) {
                continue;
            }
            // 如果是一个.class文件 而且不是目录
            if (name.endsWith(".class") && !entry.isDirectory()) {
                try {
                    classes.add(Class.forName(name.substring(0, name.lastIndexOf(".class")).replaceAll("/", "\\.")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
