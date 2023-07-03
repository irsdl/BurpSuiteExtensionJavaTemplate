// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.extension;

import com.coreyd97.BurpExtenderUtilities.Preferences;
import com.extension.ui.topMenu.TopMenuSettings;
import com.libs.objects.PreferenceObject;
import com.libs.objects.StandardSettings;

import java.util.ArrayList;
import java.util.Collection;

public class ExtensionGeneralSettings extends StandardSettings {
    public TopMenuSettings topMenuSettings;

    public ExtensionGeneralSettings(ExtensionSharedParameters sharedParameters) {
        super(sharedParameters);
    }

    @Override
    public void init() {

    }

    @Override
    public Collection<PreferenceObject> definePreferenceObjectCollection() {
        Collection<PreferenceObject> preferenceObjectCollection = new ArrayList<>();
        PreferenceObject preferenceObject;
        try {
            preferenceObject = new PreferenceObject("checkForUpdate", boolean.class, false, Preferences.Visibility.GLOBAL);
            preferenceObjectCollection.add(preferenceObject);
        } catch (Exception e) {
            //already registered setting
            sharedParameters.printDebugMessage(e.getMessage());
        }

        return preferenceObjectCollection;


    }

    @Override
    public void loadSettings() {
        if(sharedParameters.features.hasTopMenu){
            topMenuSettings = new TopMenuSettings(sharedParameters);
        }
        if (sharedParameters.preferences.safeGetSetting("checkForUpdate", false)) {
            ExtensionMainClass sharpenerBurpExtension = (ExtensionMainClass) sharedParameters.burpExtender;
            sharpenerBurpExtension.checkForUpdate();
        }
    }

    @Override
    public void unloadSettings() {
        if(sharedParameters.features.hasTopMenu && topMenuSettings != null){
            topMenuSettings.unloadSettings();
        }
    }
}
