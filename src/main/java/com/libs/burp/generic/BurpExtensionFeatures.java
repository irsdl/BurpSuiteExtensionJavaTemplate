// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.libs.burp.generic;

public class BurpExtensionFeatures {
    public boolean hasSuiteTab = false;
    public boolean hasContextMenu = false;
    public boolean hasTopMenu = false;
    public boolean isCommunityVersionCompatible = true;
    public double minSupportedMajorVersionInclusive = 0.0;
    public double maxSupportedMajorVersionInclusive = 0.0;
    public double minSupportedMinorVersionInclusive = 0.0;
    public double maxSupportedMinorVersionInclusive = 0.0;
    public boolean hasHttpHandler = false;
    public boolean hasProxyHandler = false;
    public BurpExtensionFeatures(){

    }
}
