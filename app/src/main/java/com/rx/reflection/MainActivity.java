package com.rx.reflection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            //完整的包名加类名
            printConstructor("com.rx.reflection.Person");
            getDeclaredFields("com.rx.reflection.Person");
            getMethod("com.rx.reflection.Person");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 利用反射获取构造方法
     *
     * @param className
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public void printConstructor(String className) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException
    {
        try
        {
            Class<?> aClass = Class.forName(className);
            //获取public 修饰的构造方法
            Constructor<?>[] constructors = aClass.getConstructors();
            for (int i = 0; i < constructors.length; i++)
            {
                Log.d("data", "=====constructors==========" + constructors[i]);
            }
            //获取所有的构造方法
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
            for (int j = 0; j < declaredConstructors.length; j++)
            {
                Log.d("data", "=====declaredConstructors==========" + declaredConstructors[j]);
            }
            //*获取公有、特定参数类型的构造方法  参数类型必须一致 不然会有异常抛出
            Constructor con = aClass.getConstructor(String.class, Integer.class);
            Log.d("data", "=====con==========" + con);
            //暴力访问(忽略掉访问修饰符)
            con.setAccessible(true);
            //调用构造方法
            con.newInstance("成都", 9999);
            //传入特定参数类型，获取公有或者私有构造方法
            Constructor constructor = aClass.getDeclaredConstructor(String.class, String.class, String.class);
            Log.d("data", "=====constructor==========" + constructor);
            //暴力访问(忽略掉访问修饰符)
            constructor.setAccessible(true);
            constructor.newInstance("中国", "成都", "9999");

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取成员变量并调用：
     *
     * @param className
     */
    public void getDeclaredFields(String className) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException
    {
        Class<?> mClass = Class.forName(className);
        //获取所有公有的字段
        Field[] fields = mClass.getFields();
        for (Field field : fields)
        {
            Log.d("data", "===== Field[]==========" + field);
        }
        ////获取所有的字段 包括私有、受保护、默认的
        Field[] fields1 = mClass.getDeclaredFields();
        for (Field field : fields1)
        {
            Log.d("data", "===== Field[]==========" + field);
        }

        //获取指定公有字段
        Field f = mClass.getField("country");
        //获取一个对象
        Object object = mClass.getConstructor().newInstance();
        //为字段设置值
        f.set(object, "刘德华");
        Person person = (Person) object;
        Log.d("data", "===== 赋值过后输出==========" + person.toString());

        //获取私有字段****并调用*
        Field f1 = mClass.getDeclaredField("province");
        //暴力反射，解除私有限定
        f1.setAccessible(true);
        f1.set(object, "四川");
        Log.d("data", "===== 赋值过后输出==========" + person.toString());
    }

    /**
     * 获取成员方法并调用：
     *
     * @param className
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void getMethod(String className) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException
    {
        //1.获取Class对象
        Class stuClass = Class.forName(className);
        //2.获取所有公有方法
        Method[] methodArray = stuClass.getMethods();
        for (Method m : methodArray)
        {
            Log.d("data", "===== publicMethod ==========" + m);
        }
        //获取所有的方法，包括私有的
        methodArray = stuClass.getDeclaredMethods();
        for (Method m : methodArray)
        {
            Log.d("data", "===== privateMethod ==========" + m);
        }

        //*获取公有的 getGenericHelper() 方法
        Method m = stuClass.getMethod("getGenericHelper", String.class);
        Log.d("data", "===== getGenericHelper()方法 ==========" + m);
        //实例化一个Person对象
        Object obj = stuClass.getConstructor().newInstance();
        m.invoke(obj, "刘德华 调用了：公有的，getGenericHelper()");

        //获取私有的 getGenericHelpers()方法
        m = stuClass.getDeclaredMethod("getGenericHelpers", String.class);
        //解除私有限定
        m.setAccessible(true);
        m.invoke(obj, "张学友 调用了：私有的，getGenericHelpers()");
    }
}
