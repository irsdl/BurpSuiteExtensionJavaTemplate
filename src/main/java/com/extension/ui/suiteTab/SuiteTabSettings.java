// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.extension.ui.suiteTab;

import com.extension.ExtensionSharedParameters;
import com.libs.objects.PreferenceObject;
import com.libs.objects.StandardSettings;

import java.util.Collection;

public class SuiteTabSettings extends StandardSettings {
    protected SuiteTabSettings(ExtensionSharedParameters sharedParameters) {
        super(sharedParameters);
    }

    @Override
    public void init() {

    }

    @Override
    public Collection<PreferenceObject> definePreferenceObjectCollection() {
        return null;
    }

    @Override
    public void loadSettings() {

    }

    @Override
    public void unloadSettings() {

    }
}
