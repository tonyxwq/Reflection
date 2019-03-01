package com.rx.reflection;

import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Author:XWQ
 * Time   2019/3/1
 * Descrition: this is Person
 */
public class Person
{
    public String country;
    public String city;
    private String name;
    private String province;
    private Integer height;
    private Integer age;

    public Person()
    {
        Log.d("data","========Person() 我被调用了=============");
    }

    private Person(String country, String city, String name)
    {
        this.country = country;
        this.city = city;
        this.name = name;
        Log.d("data","========我被调用了============="+country+city+name);
    }

    public Person(String country, Integer age)
    {
        this.country = country;
        this.age = age;
        Log.d("data","========我被调用了============="+country+age);
    }

    private String getMobile(String number)
    {
        String mobile = "010-110" + "-" + number;
        return mobile;
    }


    private void setCountry(String country)
    {
        this.country = country;

    }

    public void getGenericHelper(String s)
    {
        Log.d("data","调用了：公有的，getGenericHelper(): s = " + s);
    }

    private void getGenericHelpers(String s)
    {
        Log.d("data","调用了：私有的，getGenericHelpers(): s = " + s);
    }

    public Class getGenericType()
    {
        try
        {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            Method method = getClass().getDeclaredMethod("getGenericHelper", HashMap.class);
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            if (null == genericParameterTypes || genericParameterTypes.length < 1)
            {
                return null;
            }

            ParameterizedType parameterizedType = (ParameterizedType) genericParameterTypes[0];
            Type rawType = parameterizedType.getRawType();
            System.out.println("----> rawType=" + rawType);
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments == genericParameterTypes || actualTypeArguments.length < 1)
            {
                return null;
            }

            for (int i = 0; i < actualTypeArguments.length; i++)
            {
                Type type = actualTypeArguments[i];
                System.out.println("----> type=" + type);
            }
        } catch (Exception e)
        {

        }
        return null;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", height=" + height +
                '}';
    }
}
