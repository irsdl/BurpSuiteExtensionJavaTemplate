// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.extension.ui.contextMenu;

import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import java.awt.*;
import java.util.List;

public class MainContextMenu implements ContextMenuItemsProvider {

    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {
        return null;
    }
}
