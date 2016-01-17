/*
 * Copyright 2011-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-groovytestutils.
 *
 * globalpom-groovytestutils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-groovytestutils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-groovytestutils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.utils.frametesting

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.swing.SwingUtilities.*
import static javax.swing.WindowConstants.*

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension

import javax.inject.Inject
import javax.swing.JDialog
import javax.swing.JPanel

import org.fest.swing.annotation.RunsInEDT
import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.fixture.DialogFixture

import com.google.inject.assistedinject.Assisted

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

    private createDialogCallback

    private createComponentCallback

    private setupDialogCallback

    private createFixtureCallback

    /**
     * @see DialogTestingFactory#create(Map)
     */
    @Inject
    DialogTesting(@Assisted Map args) {
        args = defaultArgs(args)
        this.title = args.title
        this.size = args.size
        this.createDialogCallback = args.createDialog
        this.createComponentCallback = args.createComponent
        this.setupDialogCallback = args.setupDialog
        this.createFixtureCallback = args.createFixture
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
            def dialog = new JDialog(null, title)
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
            new DialogFixture(result)
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
