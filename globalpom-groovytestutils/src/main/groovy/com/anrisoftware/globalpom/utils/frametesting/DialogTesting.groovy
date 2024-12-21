/*
 * Copyright 2011-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.utils.frametesting

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.swing.SwingUtilities.*
import static javax.swing.WindowConstants.*

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dialog
import java.awt.Dimension
import java.awt.Frame

import javax.swing.JDialog
import javax.swing.JPanel

import org.fest.swing.annotation.RunsInEDT
import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.DialogFixture

import com.google.inject.assistedinject.Assisted

import groovy.transform.CompileStatic
import jakarta.inject.Inject

/**
 * Creates a frame fixture to test components in a dialog.
 * <p>
 * The method {@link DialogTesting#withFixture(Object...)} can be used to run
 * tests in the fixture. Multiple tests can be specified and they will be
 * run in a sequence.
 *
 * Dock testing.
 * <p>
 * <h2>Example:</h2>
 * <p>
 * <pre>
 * def injector = Guice.createInjector(new DialogTestingModule())
 * def testingFactory = injector.getInstance(DialogTestingFactory.class)
 * def title = "Dialog Test"
 * def testing = testingFactory.create([title: title])()
 * testing.withFixture({
 * 	// tests
 * })
 * </pre>
 *
 * @see TestUtils#sequencedActions(Object...)
 * @see DialogFixture
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.21
 */
@CompileStatic
class DialogTesting {

    /**
     * The current title.
     */
    final String title

    /**
     * The frame size.
     */
    final Dimension size

    /**
     * The dialog.
     */
    JDialog dialog

    /**
     * The component.
     */
    Component component

    /**
     * The current {@link DialogFixture}.
     */
    DialogFixture fixture

    private Closure createDialogCallback

    private Closure createComponentCallback

    private Closure setupDialogCallback

    private Closure createFixtureCallback

    /**
     * @see DialogTestingFactory#create(Map)
     */
    @Inject
    DialogTesting(@Assisted Map args) {
        args = defaultArgs(args)
        this.title = args.title
        this.size = args.size as Dimension
        this.createDialogCallback = args.createDialog as Closure
        this.createComponentCallback = args.createComponent as Closure
        this.setupDialogCallback = args.setupDialog as Closure
        this.createFixtureCallback = args.createFixture as Closure
    }

    private Map defaultArgs(Map args) {
        [
            title: "Dialog Test",
            size: new Dimension(680, 480),
            createDialog: null,
            createComponent: null,
            setupDialog: null,
            createFixture: null
        ] << args
    }

    /**
     * Creates the frame, dock and dialog fixture.
     *
     * @return this {@link DialogTesting}.
     */
    DialogTesting call() {
        invokeAndWait {
            dialog = createDialog()
            component = createComponent(dialog)
            setupDialog(dialog, component)
            fixture = createFixture(dialog)
        }
        this
    }

    /**
     * Creates the dialog.
     *
     * @return the {@link JDialog}.
     */
    @RunsInEDT
    JDialog createDialog() {
        if (createDialogCallback != null) {
            createDialogCallback()
        } else {
            def dialog = new JDialog((Frame)null, title)
            dialog.setDefaultCloseOperation DISPOSE_ON_CLOSE
            dialog.setSize size
            dialog.setPreferredSize size
            dialog
        }
    }

    /**
     * Creates the dialog component.
     *
     * @param dialog
     * 			  the {@link JDialog}.
     *
     * @return the {@link Component}.
     */
    @RunsInEDT
    Component createComponent(JDialog dialog) {
        if (createComponentCallback != null) {
            createComponentCallback(dialog)
        } else {
            new JPanel()
        }
    }

    /**
     * Setups the dialog.
     *
     * @param dialog
     * 			  the {@link JDialog}.
     *
     * @param component
     * 			  the {@link Component} of the dialog.
     */
    @RunsInEDT
    void setupDialog(JDialog dialog, Component component) {
        if (setupDialogCallback != null) {
            setupDialogCallback(dialog, component)
        } else {
            dialog.add component, BorderLayout.CENTER
        }
    }

    /**
     * Creates the dialog fixture.
     *
     * @param dialog
     * 			  the {@link JDialog}.
     *
     * @return the {@link FrameFixture}.
     */
    @RunsInEDT
    DialogFixture createFixture(JDialog dialog) {
        if (createFixtureCallback != null) {
            createFixtureCallback(dialog)
        } else {
            def result = GuiActionRunner.execute([executeInEDT: { dialog } ] as GuiQuery)
            new DialogFixture(result as Dialog)
        }
    }

    /**
     * Runs the tests. The {@link DialogFixture} is passed
     * to each specified test as the first argument.
     *
     * @param tests
     * 			  the tests to run.
     *
     * @return this {@link DialogTesting}
     */
    DialogTesting withFixture(Object... tests) {
        beginFixture()
        try {
            sequencedActionsWith(fixture, tests)
        } finally {
            endFixture()
        }
        this
    }

    /**
     * Creates and show the {@link FrameFixture}.
     */
    void beginFixture() {
        fixture.show()
    }

    /**
     * End the {@link FrameFixture}.
     */
    void endFixture() {
        fixture.cleanUp()
        fixture = null
    }
}
