// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.extension.ui.topMenu;
import com.extension.ExtensionMainClass;
import com.extension.ExtensionSharedParameters;
import com.libs.burp.generic.BurpExtensionSharedParameters;
import com.libs.burp.generic.BurpTitleAndIcon;
import com.libs.burp.generic.BurpUITools;
import com.libs.generic.ImageHelper;
import com.libs.generic.UIHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;


public class TopMenu extends javax.swing.JMenu {
    private JMenu topMenuForExtension;
    private final transient ExtensionSharedParameters sharedParameters;

    public TopMenu(ExtensionSharedParameters sharedParameters) {
        super(sharedParameters.extensionName);
        this.sharedParameters = sharedParameters;
        topMenuForExtension = this;
        updateTopMenuBar();
    }

    public void updateTopMenuBar() {
        SwingUtilities.invokeLater(() -> {
            removeAll();

            // Global menu
            JMenu globalMenu = new JMenu("Global Settings");
            // Debug options
            JMenu debugMenu = new JMenu("Debug Settings");
            ButtonGroup debugButtonGroup = new ButtonGroup();

            for(var item : BurpExtensionSharedParameters.DebugLevels.values()){
                JRadioButtonMenuItem debugOption = new JRadioButtonMenuItem(new AbstractAction(item.getName()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sharedParameters.preferences.safeSetSetting("debugLevel", item.getValue());
                        sharedParameters.debugLevel = item.getValue();
                    }
                });
                if (sharedParameters.debugLevel == item.getValue())
                    debugOption.setSelected(true);

                debugButtonGroup.add(debugOption);
                debugMenu.add(debugOption);
            }
            globalMenu.add(debugMenu);
            add(globalMenu);

            // Project menu
            JMenu projectMenu = new JMenu("Project Settings");
            // Example submenu
            JMenuItem changeTitle = new JMenuItem(new AbstractAction("Something Project Related!") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String newInput = UIHelper.showPlainInputMessage("Example message here", "Title of The Message Here", "Some default params", sharedParameters.get_mainFrameUsingMontoya());
                    if (newInput != null && !newInput.trim().isEmpty()) {
                        // Do stuff
                    }
                }
            });
            projectMenu.add(changeTitle);
            add(projectMenu);

            addSeparator();

            // Other items in the menu
            JMenuItem unloadExtension = new JMenuItem(new AbstractAction("Unload Extension") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        int response = UIHelper.askConfirmMessage("Sharpener Extension Unload", "Are you sure you want to unload the extension?", new String[]{"Yes", "No"}, sharedParameters.get_mainFrameUsingMontoya());
                        if (response == 0) {
                            sharedParameters.montoyaApi.extension().unload();
                        }
                    } catch (NoClassDefFoundError | Exception e) {
                        // It seems the extension is dead and we are left with a top menu bar
                    }

                    try {
                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        SwingUtilities.invokeLater(() -> {
                                            // This is useful when the extension has been unloaded but the menu is still there because of an error
                                            // We should force removing the top menu bar from Burp using all native libraries
                                            JRootPane rootPane = topMenuForExtension.getRootPane();
                                            if (rootPane != null) {
                                                JTabbedPane rootTabbedPane = (JTabbedPane) rootPane.getContentPane().getComponent(0);
                                                JFrame mainFrame = (JFrame) rootTabbedPane.getRootPane().getParent();
                                                JMenuBar mainMenuBar = mainFrame.getJMenuBar();
                                                mainMenuBar.remove(topMenuForExtension);
                                                mainFrame.validate();
                                                mainFrame.repaint();
                                            }
                                        });
                                    }
                                },
                                5000 // 5 seconds-delay to ensure all has been settled!
                        );
                    } catch (Exception e) {

                    }
                }
            });
            add(unloadExtension);

            JMenuItem resetAllSettings = new JMenuItem(new AbstractAction("Remove All Settings & Unload") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int response = UIHelper.askConfirmMessage("Sharpener Extension: Reset All Settings & Unload", "Are you sure you want to remove all the settings and unload the extension?", new String[]{"Yes", "No"}, sharedParameters.get_mainFrameUsingMontoya());
                    if (response == 0) {

                        // A bug in resetting settings in BurpExtenderUtilities should be fixed so we will give it another chance instead of using a workaround
                        // sharedParameters.resetAllSettings();
                        sharedParameters.preferences.resetAllSettings();
                        sharedParameters.montoyaApi.extension().unload();
                    }
                }
            });

            add(resetAllSettings);
            addSeparator();

            JCheckBoxMenuItem checkForUpdateOption = new JCheckBoxMenuItem("Check for Update on Start");
            checkForUpdateOption.setToolTipText("Check is done by accessing its GitHub repository");
            if (sharedParameters.preferences.safeGetBooleanSetting("checkForUpdate")) {
                checkForUpdateOption.setSelected(true);
            }

            checkForUpdateOption.addActionListener((e) -> {
                if (sharedParameters.preferences.safeGetBooleanSetting("checkForUpdate")) {
                    sharedParameters.preferences.safeSetSetting("checkForUpdate", false);
                } else {
                    sharedParameters.preferences.safeSetSetting("checkForUpdate", true);
                    ExtensionMainClass sharpenerBurpExtension = (ExtensionMainClass) sharedParameters.burpExtender;
                    sharpenerBurpExtension.checkForUpdate();
                }
            });
            add(checkForUpdateOption);

            JMenuItem showProjectPage = new JMenuItem(new AbstractAction("Project Page") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new Thread(() -> {
                        try {
                            Desktop.getDesktop().browse(new URI(sharedParameters.extensionURL));
                        } catch (Exception e) {
                        }
                    }).start();
                }
            });
            showProjectPage.setToolTipText("Will be opened in the default browser");
            add(showProjectPage);

            JMenuItem reportAnIssue = new JMenuItem(new AbstractAction("Report Bug/Feature") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new Thread(() -> {
                        try {
                            Desktop.getDesktop().browse(new URI(sharedParameters.extensionIssueTracker));
                        } catch (Exception e) {
                        }
                    }).start();
                }
            });
            reportAnIssue.setToolTipText("Will be opened in the default browser");
            add(reportAnIssue);

            addSeparator();

            Image aboutLogoImg;
            if (sharedParameters.isDarkMode) {
                aboutLogoImg = ImageHelper.scaleImageToWidth(ImageHelper.loadImageResource(sharedParameters.extensionClass, "/Logo-DarkMode.png"), 100);
            } else {
                aboutLogoImg = ImageHelper.scaleImageToWidth(ImageHelper.loadImageResource(sharedParameters.extensionClass, "/Logo-LightMode.png"), 100);
            }
            ImageIcon aboutLogoIcon = new ImageIcon(aboutLogoImg);
            JMenuItem aboutMenu = new JMenuItem(aboutLogoIcon);
            aboutMenu.setPreferredSize(new Dimension(100, 50));

            aboutMenu.setToolTipText("About this extension");
            aboutMenu.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String aboutMessage = "Burp Suite " + sharedParameters.extensionName + " - version " + sharedParameters.version +
                            "\n" + sharedParameters.extensionCopyrightMessage + "\n" +
                            "Project link: " + sharedParameters.extensionURL;
                    UIHelper.showMessage(aboutMessage, "About this extension", sharedParameters.get_mainFrameUsingMontoya());
                }
            });
            add(aboutMenu);

            // fixing the spacing when an icon is used - this used to work fine with old Java
            for (var item : getMenuComponents()) {
                if (item instanceof JMenuItem) {
                    if (((JMenuItem) item).getIcon() == null) {
                        ((JMenuItem) item).setHorizontalTextPosition(SwingConstants.LEFT);
                    }
                }
            }
        });
    }

    public void addTopMenuBar() {
        SwingUtilities.invokeLater(() -> {
            try {
                // adding toolbar
                JMenuBar menuBar = sharedParameters.get_mainMenuBarUsingMontoya();
                if (topMenuForExtension == null) {
                    topMenuForExtension = new TopMenu(sharedParameters);
                }
                //menuBar.add(topMenuForExtension, menuBar.getMenuCount() - 1);
                // to make it bold
                topMenuForExtension.setFont(topMenuForExtension.getFont().deriveFont(topMenuForExtension.getFont().getStyle() ^ Font.BOLD));
                menuBar.add(topMenuForExtension, 5); // we are adding this just after menu `Window`
                // Revalidate helps ensure the menubar picks up our change
                menuBar.revalidate();
                //sharedParameters.get_mainFrame().revalidate();
                //sharedParameters.get_mainMenuBar().repaint();
                // to set back to plain after a few seconds

                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        //topMenuForExtension.setFont(topMenuForExtension.getFont().deriveFont(topMenuForExtension.getFont().getStyle() ^ Font.BOLD)); // this would set the font so if we change them later in the UI, this menu will not be updated!
                                        if(topMenuForExtension != null)
                                            topMenuForExtension.setFont(UIManager.getLookAndFeelDefaults().getFont(topMenuForExtension.getComponent()));
                                    }
                                });
                            }
                        },
                        2000
                );
            } catch (Exception e) {
                sharedParameters.stderr.println("Error in adding the top menu: " + e.getMessage());
            }
        });

    }

    public static void removeTopMenuBarLastResort(ExtensionSharedParameters sharedParameters, boolean repaintUI) {
        if (BurpUITools.isMenuBarLoaded(sharedParameters.extensionName, sharedParameters.get_mainMenuBarUsingMontoya())) {
            // so the menu is there!
            try {
                sharedParameters.printDebugMessage("removing the menu bar the dirty way!");
                BurpUITools.removeMenuBarByName(sharedParameters.extensionName, sharedParameters.get_mainMenuBarUsingMontoya(), repaintUI);
            } catch (Exception e) {
                sharedParameters.printlnError("Error in removing the top menu the dirty way: " + e.getMessage());
            }
        }
    }

    public void removeTopMenuBar() {
        SwingUtilities.invokeLater(() -> {
            try {
                // removing old toolbar
                sharedParameters.get_mainMenuBarUsingMontoya().remove(topMenuForExtension);
            } catch (Exception e) {
                sharedParameters.stderr.println("Error in removing the top menu: " + e.getMessage());
            }
            // just in case the above did not work
            removeTopMenuBarLastResort(sharedParameters, false);

            sharedParameters.get_mainMenuBarUsingMontoya().revalidate();
            sharedParameters.get_mainMenuBarUsingMontoya().repaint();
        });
    }
}
