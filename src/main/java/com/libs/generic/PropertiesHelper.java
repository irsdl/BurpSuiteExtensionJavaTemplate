// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.libs.generic;

import java.io.InputStream;
import java.util.Properties;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class PropertiesHelper {
    public static Properties readProperties(Class claz, String resourcePath) {
        Properties prop = new Properties();
        try{
            InputStream stream = claz.getResourceAsStream(resourcePath);
            prop.load(stream);
        }catch(Exception e){
            System.err.println(e.getMessage() + "\r\n" + getStackTrace(e));
        }
        return prop;
    }
}
