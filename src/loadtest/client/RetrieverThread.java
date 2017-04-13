package loadtest.client;

import java.net.*;
import java.io.*;

/**
 * This class is the thread which will contact the server and read the results.
 * There will be as many instances of this thread as the user indicates in the
 * UI of this application.
 */
public class RetrieverThread extends Thread
{
    /**
     * The URL for the server this thread will access
     */
    private URL m_server;
    /**
     * The names of the parameters this thread will pass to the server.
     */
    private String m_paramNames[];
    /**
     * The values of the parameters this thread will pass to the server.
     */
    private String m_paramVals[];

    /**
     * This condition is used when the thread is put into a waiting state.
     */
    private boolean m_cond = true;

    /**
     * This variable will hold any exceptions which occur when communicating
     * with the server.
     */
    private Exception m_exception;

    /**
     * Indicates if this thread was successful or not.
     */
    private boolean m_worked = false;

    /**
     * Indicates the thread should stop.
     */
    private boolean m_shouldStop;
    /**
     * Lets the thread manager know if this thread is currently running or
     * waiting.
     */
    private boolean m_isRunning = false;

    /**
     * This is a count of all the threads which have been created. This number
     * is used to give this thread a unique name.
     */
    private static int g_threadCount;
    /**
     * The number of the current instance of this thread.
     */
    private int m_threadNumber;

    /**
     * The amount of time this thread took to run.
     */
    private long m_time;

    /**
     * An indicator used when adding parameters to the URL.
     */
    private boolean m_firstParam = true;

    public RetrieverThread(URL server, String paramNames[], String paramVals[])
    {
        m_server = server;
        m_paramNames = paramNames;
        m_paramVals = paramVals;
        m_threadNumber = g_threadCount;
        g_threadCount++;
    }

    public String toString()
    {
        return "Client Thread " + (m_threadNumber + 1);
    }

    public Exception getException()
    {
        return m_exception;
    }

    public void doStop()
    {
        /*
         * Once we have set the flag to indicate this thread should stop we need
         * to notify the thread so it can stop waiting and let the run method
         * return.
         */
        m_shouldStop = true;
        stopWait();
    }

    public void run()
    {
        /*
         * This run method will work in a simple loop. It will execute the
         * getData method and then wait until it is notified again. This loop
         * will continue until the doStop method is called.
         */
        while (true) {
            if (m_shouldStop)
                return;

            m_isRunning = true;
            getData();
            m_isRunning = false;
            doWait();
        }
    }

    /*
     * This is the method which will actually get the data from the server.
     */
    private void getData()
    {
        /*
         * Step 1. Reset all the variables from the last time the thread ran.
         */
        m_time = -1;
        m_worked = false;
        m_exception = null;
        m_firstParam = true;

        /*
         * Step 2. Create the HTTP connection.
         */
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) m_server.openConnection();

            /*
             * Step 3. Set properties on the connection. This connection will
             * use the POST method, will not follow redirects, and will use the
             * form URL encoded content type for parameters.
             */
            conn.setRequestMethod("POST");
            conn.setFollowRedirects(false);

            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            /*
             * Step 4. Write the parameters out to the connection.
             */
            Writer out = new OutputStreamWriter(conn.getOutputStream());
            for (int i = 0; i < m_paramNames.length; i++) {
                addParameter(out, m_paramNames[i], m_paramVals[i]);
            }

            /*
             * Step 5. Send the data to the server the close the output stream.
             */
            out.flush();
            out.close();

            /*
             * Step 6. Record the starting time and connect to the server.
             */
            long startTime = System.nanoTime();
            conn.connect();

            /*
             * Step 7. Handle the server response.
             */
            try {
                /*
                 * A response of 302 indicates that the URL has been temporarily
                 * moved so we will get the new URL and try again.
                 */
                if (conn.getResponseCode() == 302) {
                    System.out.println("We got a 302");
                    m_server = new URL(conn.getHeaderField("Location"));
                    getData();
                    return;
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }

            /*
             * A response of 401 indicates that we were denied access to the
             * URL.
             */
            if (conn.getResponseCode() == 401) {
                System.out.println("We got a 401 and were denied acces to the URL.");
                return;
            }

            // System.out.println("conn.getResponseCode(): " +
            // conn.getResponseCode());
            // System.out.println("conn.getResponseMessage(): " +
            // conn.getResponseMessage());

            /*
             * Step 8. Parse the output from the server.
             */
            InputStream in = conn.getInputStream();
            m_worked = parseSuccess(in);

            /*
             * Step 9. Record the amount of time it took to talk to the server.
             */
            m_time = System.nanoTime() - startTime;
        } catch (Exception e) {
            e.printStackTrace();
            m_exception = e;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    /*
     * This method will parse the output of the server and see if it returned
     * successfully. This sample is very simple and just searches for the text
     * "Everything worked fine". You can make this method as complicated as you
     * need it to be.
     */
    private static boolean parseSuccess(InputStream in)
        throws IOException
    {
        LineNumberReader lnr = new LineNumberReader(new BufferedReader(new InputStreamReader(in)));

        String line = null;

        while ((line = lnr.readLine()) != null) {
            if (line.indexOf("Everything worked fine") != -1) {
                return true;
            }
        }

        return false;
    }

    /**
     * This is a helper method to add form parameters to the URL
     * 
     * @param out
     *        the output stream to write the parameters to
     * @param name
     *        the name of the paramter
     * @param value
     *        the value of the parameter
     * @exception IOException
     */
    private void addParameter(Writer out, String name, String value)
        throws IOException
    {
        if (m_firstParam)
            m_firstParam = false;
        else
            out.write("&");

        out.write(URLEncoder.encode(name));
        out.write("=");
        out.write(URLEncoder.encode(value));

    }

    /**
     * This method indicates that the thread should stop waiting and run again.
     */
    protected synchronized void stopWait()
    {
        this.notify();
    }

    /**
     * This method will cause the thread to wait until the stopWait method is
     * called
     */
    protected synchronized void doWait()
    {
        m_cond = true;
        while (m_cond) {
            try {
                this.wait();
                m_cond = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Indicates if this thread is currently running or waiting.
     * 
     * @return true if the thread is running and false if it is waiting
     */
    protected boolean isRunning()
    {
        return m_isRunning;
    }

    /**
     * Indicates how long this thread ran for.
     * 
     * @return the amount of time it took the thread to rn in nano seconds
     */
    protected long getTime()
    {
        return m_time;
    }

    /**
     * Indicates if this thread was successful.
     * 
     * @return true if the thread completed successfully and false otherwise.
     */
    protected boolean worked()
    {
        return m_worked;
    }
}
