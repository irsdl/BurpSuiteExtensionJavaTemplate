// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.extension.ui.topMenu;

import com.extension.ExtensionSharedParameters;
import com.libs.objects.PreferenceObject;
import com.libs.objects.StandardSettings;

import java.util.Collection;
import java.util.Collections;

public class TopMenuSettings extends StandardSettings {

    public TopMenuSettings(ExtensionSharedParameters sharedParameters) {
        super(sharedParameters);
        sharedParameters.printDebugMessage("TopMenuSettings");
    }
    @Override
    public void init() {

    }

    @Override
    public Collection<PreferenceObject> definePreferenceObjectCollection() {
        return Collections.emptyList();
    }

    @Override
    public void loadSettings() {
        // Adding the top menu
        if(sharedParameters.features.hasTopMenu){
            try {
                if (sharedParameters.topMenuBar != null) {
                    sharedParameters.printDebugMessage("Removing the top menu before adding it again");
                    sharedParameters.topMenuBar.removeTopMenuBar();
                }
                sharedParameters.printDebugMessage("Adding the top menu");
                sharedParameters.topMenuBar = new TopMenu(sharedParameters);
                sharedParameters.topMenuBar.addTopMenuBar();
            } catch (Exception e) {
                sharedParameters.stderr.println("Error in creating the top menu: " + e.getMessage());
            }
        }
    }

    @Override
    public void unloadSettings() {
        sharedParameters.printDebugMessage("removing toolbar menu");
        // removing toolbar menu
        if (sharedParameters.topMenuBar != null)
            sharedParameters.topMenuBar.removeTopMenuBar();
    }
}
