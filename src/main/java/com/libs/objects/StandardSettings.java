// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.libs.objects;

import com.libs.burp.generic.BurpExtensionSharedParameters;
import com.extension.ExtensionSharedParameters;

import java.util.Collection;

public abstract class StandardSettings {
    private Collection<PreferenceObject> _preferenceObjectCollection;
    public ExtensionSharedParameters sharedParameters;

    protected StandardSettings(ExtensionSharedParameters sharedParameters) {
        boolean isError = false;
        this.sharedParameters = sharedParameters;
        try{
            init();
            this._preferenceObjectCollection = definePreferenceObjectCollection();
            registerSettings();
            loadSettings();
        }catch(Exception e){
            isError = true;
            sharedParameters.printException(e);
        }

        if(isError){
            sharedParameters.printlnError("A fatal error has occurred in loading the settings. The extension is going to be unloaded.");
            sharedParameters.montoyaApi.extension().unload();
        }
    }

    public abstract void init();

    public abstract Collection<PreferenceObject> definePreferenceObjectCollection();

    public abstract void loadSettings();

    public abstract void unloadSettings();

    public Collection<PreferenceObject> get_preferenceObjectCollection() {
        return _preferenceObjectCollection;
    }

    private void registerSettings() {
        if (_preferenceObjectCollection == null)
            return;

        for (PreferenceObject preferenceObject : _preferenceObjectCollection) {
            try {
                sharedParameters.preferences.registerSetting(preferenceObject.settingName, preferenceObject.type, preferenceObject.defaultValue, preferenceObject.visibility);
            } catch (Exception e) {
                //already registered setting
                sharedParameters.printDebugMessage(e.getMessage());
                if (sharedParameters.debugLevel == BurpExtensionSharedParameters.DebugLevels.VeryVerbose.getValue())
                    e.printStackTrace(sharedParameters.stderr);
            }
        }
    }

    public void resetSettings() {
        if (_preferenceObjectCollection == null)
            return;

        for (PreferenceObject preferenceObject : _preferenceObjectCollection) {
            try {
                // sharedParameters.preferences.resetSetting(preferenceObject.settingName); // there is a bug in the library, so we can't use this for now
                sharedParameters.preferences.safeSetSetting(preferenceObject.settingName, null);
            } catch (Exception e) {
                sharedParameters.printDebugMessage(e.getMessage());
                if (sharedParameters.debugLevel == BurpExtensionSharedParameters.DebugLevels.VeryVerbose.getValue())
                    e.printStackTrace(sharedParameters.stderr);
            }
        }
    }
}
