/*******************************************************************************
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
 *
 ******************************************************************************/
package loadtest.client;

import java.util.HashMap;

import java.io.*;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.net.MalformedURLException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * This class is the main UI for our load testing client. This class will handle
 * the UI and manage all of the thread calling the server.
 */
public class Main extends JPanel implements ActionListener
{
    /*
     * These two arrays are the list of variables we will pass when each thread
     * runs. You can change these parameters to be whatever you want.
     */
    private static final String PARAM_NAMES[] = {
//        "cName", "cType", "destination", "cWeight", "uName", "submit"
    	"value1", "value2", "value3", "submit"
    };
    private static final String PARAM_VALUES[] = {
//        "cName", "foods", "destination", "100", "uName", "Add Container"
        "value1", "value2", "value3", "submit"
    };

    public static void main(String args[])
    {
        setNativeLookAndFeel();

        JFrame mainFrame = new JFrame();
        mainFrame = new JFrame("Load Test Client");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Main w = new Main();

        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().add(w, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void setNativeLookAndFeel()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
    }

    /**
     * A spinner to let users choose the number of threads they want to use
     */
    private JSpinner m_threadCount;
    /**
     * A spinner to let the users choose the number of times they want the test
     * to run.
     */
    private JSpinner m_count;
    /**
     * This variable will hold the number of times have executed the test.
     */
    private int m_opCount;
    /**
     * The button allowing the user to start the test
     */
    private JButton m_start;
    /**
     * A text field where the user can specify the URL to their server.
     */
    private JTextField m_url;

    /**
     * We will use this timer to know when to look at the threads and see which
     * ones need to be restarted.
     */
    private Timer m_shortTimer;

    /**
     * This HashMap will hold onto the threads which are running and the number
     * of times each of those threads has run.
     */
    private HashMap m_threads = new HashMap();

    private Main()
    {
        setOpaque(true);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        JLabel label = new JLabel("Server URL:");
        add(label);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weighty = 0.0001;
        gbc.weightx = 0.0001;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        layout.setConstraints(label, gbc);

        /*
         * The server URL
         */
        m_url = new JTextField();
//        m_url.setText("http://localhost:8081/ShipWare/Containers.html");
        m_url.setText("http://localhost:8080/ShipWare/src/loadtest.server/MainForm.html");
        add(m_url);
        gbc.weightx = 0.9;
        gbc.gridx++;
        layout.setConstraints(m_url, gbc);

        /*
         * The thread count spinner
         */
        label = new JLabel("Number of Threads:");
        add(label);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.0001;
        layout.setConstraints(label, gbc);

        m_threadCount = new JSpinner();
        m_threadCount.setValue(Integer.valueOf(5));
        add(m_threadCount);
        gbc.gridx++;
        gbc.weightx = 0.9;
        layout.setConstraints(m_threadCount, gbc);

        /*
         * The operation count
         */
        label = new JLabel("Number of Iterations:");
        add(label);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.0001;
        layout.setConstraints(label, gbc);

        m_count = new JSpinner();
        m_count.setValue(Integer.valueOf(5));
        add(m_count);
        gbc.gridx++;
        gbc.weightx = 0.9;
        layout.setConstraints(m_count, gbc);

        m_start = new JButton("Start");
        m_start.addActionListener(this);
        add(m_start);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        layout.setConstraints(m_start, gbc);

        JPanel spacer = new JPanel();
        add(spacer);
        spacer.setOpaque(false);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weighty = 0.9;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(spacer, gbc);
    }

    /**
     * This method will reset our thread pool and restart all of the threads.
     */
    private void doStart()
    {
        try {
            m_threads.clear();
            int threadCount = ((Integer) m_threadCount.getValue()).intValue();
            m_opCount = ((Integer) m_count.getValue()).intValue() - 1;
            for (int i = 0; i < threadCount; i++) {
                RetrieverThread thread = new RetrieverThread(new URL(m_url.getText()), PARAM_NAMES, PARAM_VALUES);
                m_threads.put(thread, Integer.valueOf(0));
                thread.start();
            }

            m_shortTimer = new Timer(100, this);
            m_shortTimer.start();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "Invalid URL: " + m_url.getText());
        }
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("h:mm:ss:SS");

    private static String formatTime(long time)
    {
        return (time / 1000000) + " milliseconds";
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == m_shortTimer) {
            boolean foundOne = false;
            Object keys[] = m_threads.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                RetrieverThread thread = (RetrieverThread) keys[i];
                int currentCount = ((Integer) m_threads.get(thread)).intValue();
                if (!thread.isRunning()) {
                    /*
                     * If the thread is not running then we want to print the
                     * result and restart it.
                     */
                    if (currentCount <= m_opCount) {
                        if (thread.getException() != null) {
                            System.out.println(thread + " (Iteration: " + currentCount + ") - " + 
                                               formatTime(thread.getTime()) + " result: " + 
                                               thread.getException().getMessage());
                        } else if (!thread.worked()) {
                            System.out.println(thread + " (Iteration: " + currentCount + ") - " + 
                                               formatTime(thread.getTime()) + " result: FAILED");
                        } else {
                            System.out.println(thread + " (Iteration: " + currentCount + ") - " + 
                                               formatTime(thread.getTime()) + " result: SUCCESS");
                        }

                        // Indicate that the test is not done yet.
                        foundOne = true;

                        // Restart the thread.
                        thread.stopWait();

                        // Add the thread back to our thread pool with a new
                        // number.
                        m_threads.remove(thread);
                        m_threads.put(thread, Integer.valueOf(currentCount + 1));

                    } else {
                        /*
                         * If the cound for this thread is greater than the
                         * number of iterations we want then we can just stop
                         * the thread. It is important that we call this stop
                         * method so the run method of the thread can return
                         * properly. If we didn't do that then the thread would
                         * just stay active and waiting and we would have a
                         * memory leak.
                         */
                        thread.doStop();
                    }
                } else {
                    foundOne = true;
                }
            }

            if (!foundOne) {
                /*
                 * If there are no more running threads then we can tell the
                 * user that the test is done.
                 */
                JOptionPane.showMessageDialog(this, "All Threads Completed");
                m_shortTimer.stop();
            }
        } else if (e.getSource() == m_start) {
            doStart();
        }
    }

}
