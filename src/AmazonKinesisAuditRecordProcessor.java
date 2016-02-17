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

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ThrottlingException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;




import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Processes records and checkpoints progress.
 */
public class AmazonKinesisAuditRecordProcessor   implements IRecordProcessor {

    private static final Log LOG = LogFactory.getLog(AmazonKinesisAuditRecordProcessor.class);
    private String kinesisShardId;

    // Backoff and retry settings
    private static final long BACKOFF_TIME_IN_MILLIS = 3000L;
    private static final int NUM_RETRIES = 10;

    // Checkpoint about once a minute
    private static final long CHECKPOINT_INTERVAL_MILLIS = 60000L;
    private long nextCheckpointTimeInMillis;

    private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String shardId) {
        LOG.info("Initializing record processor for shard: " + shardId);
        this.kinesisShardId = shardId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
        LOG.info("Processing " + records.size() + " records from " + kinesisShardId);

        // Process records and perform all exception handling.
        processRecordsWithRetries(records);

        // Checkpoint once every checkpoint interval.
        if (System.currentTimeMillis() > nextCheckpointTimeInMillis) {
            checkpoint(checkpointer);
            nextCheckpointTimeInMillis = System.currentTimeMillis() + CHECKPOINT_INTERVAL_MILLIS;
        }
    }

    /**
     * Process records performing retries as needed. Skip "poison pill" records.
     * 
     * @param records Data records to be processed.
     */
    private void processRecordsWithRetries(List<Record> records) {
        for (Record record : records) {
            boolean processedSuccessfully = false;
            for (int i = 0; i < NUM_RETRIES; i++) {
                try {
                    //
                    // Logic to process record goes here.
                    //
                    processSingleRecord(record);

                    processedSuccessfully = true;
                    break;
                } catch (Throwable t) {
                    LOG.warn("Caught throwable while processing record " + record, t);
                }

                // backoff if we encounter an exception.
                try {
                    Thread.sleep(BACKOFF_TIME_IN_MILLIS);
                } catch (InterruptedException e) {
                    LOG.debug("Interrupted sleep", e);
                }
            }

            if (!processedSuccessfully) {
                LOG.error("Couldn't process record " + record + ". Skipping the record.");
            }
        }
    }

    /**
     * Process a single record.
     * 
     * @param record The record to be processed.
     */
    private void processSingleRecord(Record record) {
        // TODO Add your own record processing logic here
   	
    	String data = null;
        String App_uuid = "4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f";
        try {
        	// For this app, we interpret the payload as UTF-8 chars.
        	  data = decoder.decode(record.getData()).toString();
        	  Boolean Verify = false;
        	 String str = data;
            
            String output =new String();
            String[] delims = {"\"audit_uuid\":","\"where_uri\":","\"who_uri\":","\"what_uri\":","\"which_changed\":","\"why\":","\"when_audited\":","\"when_persisted\":","\"corrected_audit_uuid\":"};
            String[] words = new String[delims.length+1];

            for (int i = 1; i <delims.length ; i++)
            {
            StringTokenizer st = new StringTokenizer(str, delims[i]);
            		String[] tokens = str.split(delims[i]);
            		String phrase = new String();
            		int tokenCount = tokens.length;
            		for (int j = 0; j < tokenCount; j++) 
            		{
            		if(j==0)
            			{
            				tokens[j] = tokens[j].replace("{\"audit_uuid\":","");
            				tokens[j] = tokens[j].replace("\"where_uri\":","");
            				tokens[j] = tokens[j].replace("\"who_uri\":","");
            				tokens[j] = tokens[j].replace("\"what_uri\":","");
            				tokens[j] = tokens[j].replace("\"which_changed\":","");
            				tokens[j] = tokens[j].replace("\"why\":","");
            				tokens[j] = tokens[j].replace("\"when_audited\":","");
            				tokens[j] = tokens[j].replace("\"when_persisted\":","");
            				tokens[j] = tokens[j].replace("\"corrected_audit_uuid\":","");
            				phrase = tokens[j];
            				String rephrase = null;
            				if (phrase != null && phrase.length() > 1)
            				{
            					rephrase = phrase.substring(0,phrase.length() - 1);
            					words[i]= rephrase;
            					}
            				}
            			if(j==1)
            			{
            				str = tokens[j];
            				if(i==delims.length-1)
            				{
            					words[delims.length-1]= str;}
            				}
            		}
            		}
            		for (int k = 1; k <delims.length ; k++)
            		{
            			if (k==2)
            			{
            				Verify = words[k].contains(App_uuid);
            				if (Verify)
            				{
            					System.out.println("\nAudit: "+ data);
            					Verify = false;
            				}
            			}
            			if (k==3)
            			{
                            //System.out.println("\nwho_uri :"+ words[k]);
                         }
            			
            		}
               
        }
        catch (CharacterCodingException e) {
            //do something clever with the exception
        	LOG.error("Malformed data: " + data, e);
        } 
        finally {
        	
        }
    }  
    
    public void findFields() {
        String str = "[field1][field2][field3]field4";
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);
        List<String> fields = new ArrayList<String>();
        while(matcher.find()) {
            fields.add(matcher.group(1));
        }
        matcher.replaceAll("");
        StringBuffer tail = new StringBuffer();
        matcher.appendTail(tail);
        fields.add(tail.toString());
        System.out.println(fields);
    }
    
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
        LOG.info("Shutting down record processor for shard: " + kinesisShardId);
        // Important to checkpoint after reaching end of shard, so we can start processing data from child shards.
        if (reason == ShutdownReason.TERMINATE) {
            checkpoint(checkpointer);
        }
    }

    /** Checkpoint with retries.
     * @param checkpointer
     */
    private void checkpoint(IRecordProcessorCheckpointer checkpointer) {
        LOG.info("Checkpointing shard " + kinesisShardId);
        for (int i = 0; i < NUM_RETRIES; i++) {
            try {
                checkpointer.checkpoint();
                break;
            } catch (ShutdownException se) {
                // Ignore checkpoint if the processor instance has been shutdown (fail over).
                LOG.info("Caught shutdown exception, skipping checkpoint.", se);
                break;
            } catch (ThrottlingException e) {
                // Backoff and re-attempt checkpoint upon transient failures
                if (i >= (NUM_RETRIES - 1)) {
                    LOG.error("Checkpoint failed after " + (i + 1) + "attempts.", e);
                    break;
                } else {
                    LOG.info("Transient issue when checkpointing - attempt " + (i + 1) + " of "
                            + NUM_RETRIES, e);
                }
            } catch (InvalidStateException e) {
                // This indicates an issue with the DynamoDB table (check for table, provisioned IOPS).
                LOG.error("Cannot save checkpoint to the DynamoDB table used by the Amazon Kinesis Client Library.", e);
                break;
            }
            try {
                Thread.sleep(BACKOFF_TIME_IN_MILLIS);
            } catch (InterruptedException e) {
                LOG.debug("Interrupted sleep", e);
            }
        }
    }
}
