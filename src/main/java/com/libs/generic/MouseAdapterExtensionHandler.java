// Burp Suite Extension Template
// The template has been developed by Soroush Dalili (@irsdl)
// The template has been released as open source by MDSec - https://www.mdsec.co.uk
// Released under AGPL see LICENSE for more information

package com.libs.generic;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;


public class MouseAdapterExtensionHandler extends MouseAdapter {

    Consumer<MouseEvent> mouseEventConsumer = null;
    int mouseEventTrigger = -1;

    public MouseAdapterExtensionHandler(Consumer<MouseEvent> consumer) {
        this(consumer, MouseEvent.MOUSE_CLICKED);
    }

    public MouseAdapterExtensionHandler(Consumer<MouseEvent> consumer, int mouseEventTrigger) {
        this.mouseEventConsumer = consumer;
        this.mouseEventTrigger = mouseEventTrigger;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mouseEventTrigger == MouseEvent.MOUSE_CLICKED) {
            this.mouseEventConsumer.accept(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (mouseEventTrigger == MouseEvent.MOUSE_PRESSED) {
            this.mouseEventConsumer.accept(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // we can use mousePressed or mouseReleased instead of mouseClicked to detect a click when tabs were wrapped
        // mouseReleased has been used to show the menu in the right place when tabs were wrapped
        if (mouseEventTrigger == MouseEvent.MOUSE_RELEASED) {
            this.mouseEventConsumer.accept(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (mouseEventTrigger == MouseEvent.MOUSE_ENTERED) {
            this.mouseEventConsumer.accept(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (mouseEventTrigger == MouseEvent.MOUSE_EXITED) {
            this.mouseEventConsumer.accept(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseEventTrigger == MouseEvent.MOUSE_DRAGGED) {
            this.mouseEventConsumer.accept(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mouseEventTrigger == MouseEvent.MOUSE_MOVED) {
            this.mouseEventConsumer.accept(e);
        }
    }
}
