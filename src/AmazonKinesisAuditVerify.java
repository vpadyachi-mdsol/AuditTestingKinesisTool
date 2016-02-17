/*
 * Copyright 2012-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */


import java.net.InetAddress;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.io.IOException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;


import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;

/**
 * Sample Amazon Kinesis Application.
 */
public final class AmazonKinesisAuditVerify {
	
    /*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *      (/Users/vpadyachi/.aws/credentials) where the sample code will load the
     *      credentials from.
     *      https://console.aws.amazon.com/iam/home?#security_credential
     *
     * WARNING:
     *      To avoid accidental leakage of your credentials, DO NOT keep
     *      the credentials file in your source directory.
     */

    public static final String SAMPLE_APPLICATION_STREAM_NAME = "myFirstStream";

    private static final String SAMPLE_APPLICATION_NAME = "SampleKinesisApplication";

    // Initial position in the stream when the application starts up for the first time.
    // Position can be one of LATEST (most recent data) or TRIM_HORIZON (oldest available data)
   // private static final InitialPositionInStream SAMPLE_APPLICATION_INITIAL_POSITION_IN_STREAM =
   //         InitialPositionInStream.LATEST;

   private static AWSCredentialsProvider credentialsProvider;

   private static void init() {
        // Ensure the JVM will refresh the cached IP values of AWS resources (e.g. service endpoints).
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/Users/vpadyachi/.aws/credentials).
         */
        credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Users/vpadyachi/.aws/credentials), and is in valid format.", e);
        }
    }

    private static final String DEFAULT_APP_NAME = "SampleKinesisApplication"; 
    private static final String DEFAULT_STREAM_NAME = "audits_persisted_sandbox"; 
     
    private static final String DEFAULT_KINESIS_ENDPOINT = "https://kinesis.us-east-1.amazonaws.com"; 
     
    // Initial position in the stream when the application starts up for the first time. 
    // Position can be one of LATEST (most recent data) or TRIM_HORIZON (oldest available data) 
    private static final InitialPositionInStream DEFAULT_INITIAL_POSITION = InitialPositionInStream.TRIM_HORIZON; 
     
    private static String applicationName = DEFAULT_APP_NAME; 
    private static String streamName = DEFAULT_STREAM_NAME; 
    private static String kinesisEndpoint = DEFAULT_KINESIS_ENDPOINT; 
    private static InitialPositionInStream initialPositionInStream = DEFAULT_INITIAL_POSITION; 
     
    private static KinesisClientLibConfiguration kinesisClientLibConfiguration; 
     
    private static final Log LOG = LogFactory.getLog(AmazonKinesisAuditVerify.class); 
 
    /**
     *  
     */ 
    private AmazonKinesisAuditVerify() { 
        super(); 
    }
	
	
    public static void main(String[] args) throws IOException {
       EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AmazonKinesisAuditVerify window = new AmazonKinesisAuditVerify();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
       
       init(); 
/*
        if (args.length == 1 && "delete-resources".equals(args[0])) {
            deleteResources();
            return;
        }

        String workerId = InetAddress.getLocalHost().getCanonicalHostName() + ":" + UUID.randomUUID();
        KinesisClientLibConfiguration kinesisClientLibConfiguration =
                new KinesisClientLibConfiguration(SAMPLE_APPLICATION_NAME,
                        SAMPLE_APPLICATION_STREAM_NAME,
                        credentialsProvider,
                        workerId);
        kinesisClientLibConfiguration.withInitialPositionInStream(SAMPLE_APPLICATION_INITIAL_POSITION_IN_STREAM);

        IRecordProcessorFactory recordProcessorFactory = new AmazonKinesisApplicationRecordProcessorFactory();
        Worker worker = new Worker(recordProcessorFactory, kinesisClientLibConfiguration);

        System.out.printf("Running %s to process stream %s as worker %s...\n",
                SAMPLE_APPLICATION_NAME,
                SAMPLE_APPLICATION_STREAM_NAME,
                workerId);

        int exitCode = 0;
        try {
            worker.run();
        } catch (Throwable t) {
            System.err.println("Caught throwable while processing data.");
            t.printStackTrace();
            exitCode = 1;
        }
        System.exit(exitCode);
        */
        String propertiesFile = null; 
        
        if (args.length > 1) { 
            System.err.println("Usage: java " + AmazonKinesisAuditVerify.class.getName() + " <propertiesFile>"); 
            System.exit(1); 
        } else if (args.length == 1) { 
            propertiesFile = args[0]; 
        } 
 
        configure(propertiesFile); 
         
        System.out.println("Starting " + applicationName); 
        LOG.info("Running " + applicationName + " to process stream " + streamName); 
         
         
        IRecordProcessorFactory recordProcessorFactory = new AmazonKinesisAuditRecordProcessorFactory(); 
         Worker worker = new Worker(recordProcessorFactory, kinesisClientLibConfiguration); 
 
        int exitCode = 0; 
        try { 
            worker.run(); 
        } catch (Throwable t) { 
            LOG.error("Caught throwable while processing data.", t); 
            exitCode = 1; 
        } 
        System.exit(exitCode);
    }

    private static void configure(String propertiesFile) throws IOException { 
        
        
        if (propertiesFile != null) { 
            loadProperties(propertiesFile); 
        } 
         
        // ensure the JVM will refresh the cached IP values of AWS resources (e.g. service endpoints). 
        java.security.Security.setProperty("networkaddress.cache.ttl" , "60"); 
         
        String workerId = InetAddress.getLocalHost().getCanonicalHostName() + ":" + UUID.randomUUID(); 
        LOG.info("Using workerId: " + workerId); 
        
        // Get credentials from IMDS. If unsuccessful, get them from the classpath.  
       //AWSCredentialsProvider credentialsProvider = null; 
        try { 
            credentialsProvider = new ProfileCredentialsProvider();
            // Verify we can fetch credentials from the provider 
            credentialsProvider.getCredentials(); 
            LOG.info("Obtained credentials from the IMDS."); 
        } catch (AmazonClientException e) { 
            LOG.info("Unable to obtain credentials from the IMDS, trying classpath properties", e); 
            credentialsProvider = new ClasspathPropertiesFileCredentialsProvider(); 
            // Verify we can fetch credentials from the provider 
            credentialsProvider.getCredentials(); 
            LOG.info("Obtained credentials from the properties file."); 
        } 
         
        LOG.info("Using credentials with access key id: " + credentialsProvider.getCredentials().getAWSAccessKeyId()); 
         
        kinesisClientLibConfiguration = new KinesisClientLibConfiguration(applicationName, streamName,  
          credentialsProvider, workerId).withInitialPositionInStream(initialPositionInStream);  
    }  
    
    /**
     * @param propertiesFile 
     * @throws IOException Thrown when we run into issues reading properties 
     */ 
    private static void loadProperties(String propertiesFile) throws IOException { 
        FileInputStream inputStream = new FileInputStream(propertiesFile); 
        Properties properties = new Properties(); 
        try { 
            properties.load(inputStream); 
        } finally { 
            inputStream.close(); 
        } 
         
        String appNameOverride = properties.getProperty(ConfigKeys.APPLICATION_NAME_KEY); 
        if (appNameOverride != null) { 
            applicationName = appNameOverride; 
        } 
        LOG.info("Using application name " + applicationName); 
         
        String streamNameOverride = properties.getProperty(ConfigKeys.STREAM_NAME_KEY); 
        if (streamNameOverride != null) { 
            streamName = streamNameOverride; 
        } 
        LOG.info("Using stream name " + streamName); 
         
        String kinesisEndpointOverride = properties.getProperty(ConfigKeys.KINESIS_ENDPOINT_KEY); 
        if (kinesisEndpointOverride != null) { 
            kinesisEndpoint = kinesisEndpointOverride; 
        } 
        String initialPositionOverride = properties.getProperty(ConfigKeys.INITIAL_POSITION_IN_STREAM_KEY); 
        if (initialPositionOverride != null) { 
             initialPositionInStream = InitialPositionInStream.valueOf(initialPositionOverride); 
        } 
         LOG.info("Using initial position " + initialPositionInStream.toString() + " (if a checkpoint is not found)."); 
    }
    
    public static void deleteResources() {
        AWSCredentials credentials = credentialsProvider.getCredentials();

        // Delete the stream
        AmazonKinesis kinesis = new AmazonKinesisClient(credentials);
        System.out.printf("Deleting the Amazon Kinesis stream used by the sample. Stream Name = %s.\n",
                SAMPLE_APPLICATION_STREAM_NAME);
        try {
            kinesis.deleteStream(SAMPLE_APPLICATION_STREAM_NAME);
        } catch (ResourceNotFoundException ex) {
            // The stream doesn't exist.
        }

        // Delete the table
        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(credentialsProvider.getCredentials());
        System.out.printf("Deleting the Amazon DynamoDB table used by the Amazon Kinesis Client Library. Table Name = %s.\n",
                SAMPLE_APPLICATION_NAME);
        try {
            dynamoDB.deleteTable(SAMPLE_APPLICATION_NAME);
        } catch (com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException ex) {
            // The table doesn't exist.
        }
    }
}
