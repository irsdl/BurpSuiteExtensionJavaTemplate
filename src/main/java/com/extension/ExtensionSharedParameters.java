// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.extension;

import burp.api.montoya.MontoyaApi;
import com.libs.burp.generic.BurpExtensionSharedParameters;
import com.extension.ui.topMenu.TopMenu;

public class ExtensionSharedParameters extends BurpExtensionSharedParameters {
    public ExtensionGeneralSettings allSettings;

    public ExtensionSharedParameters(ExtensionMainClass extensionMainClass, MontoyaApi api, String propertiesFilePath) {
        super(extensionMainClass, api, propertiesFilePath);
        loadingChecks();
    }

    private void loadingChecks(){
        // do stuff such as setting an initial parameter based on Burp suite version or its title etc.

    }
}
