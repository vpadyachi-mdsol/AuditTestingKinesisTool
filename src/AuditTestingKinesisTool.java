import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AuditTestingKinesisTool implements ActionListener {

	private JFrame frame;
	private AuditTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuditTestingKinesisTool window = new AuditTestingKinesisTool();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AuditTestingKinesisTool() {
		initialize();
	   
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLoadaudits = new JButton("LoadAudits");
		btnLoadaudits.setBounds(314, 6, 117, 29);
		frame.getContentPane().add(btnLoadaudits);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		scrollPane.setBounds(46, 47, 700, 500);
		frame.getContentPane().add(scrollPane);
		
		table = new AuditTable();
		scrollPane.setViewportView(table);
		
		 //(1) Create the Table Model
	    DefaultTableModel dm = new DefaultTableModel();

		// Names for each of the columns
		String[] columnNames = {
				"Audits"
			};

		// the actual data values
		String[][] data = new String[10][1];

		//This is just a mockup of data at this time until the AuditTable value gets populated automatically
		//populate the data matrix
		for (int row = 0; row < 10; row++){
			data[0][0] = new String("{ audit_uuid : 64fc6047-d704-4085-a199-681527023220 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:a61fb567-b863-43a8-ad36-9d5b5b455d7d , what_uri : com:mdsol:study_cells:e0aa0cab-bc68-4f16-9638-c364d1994c02 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : e0aa0cab-bc68-4f16-9638-c364d1994c02 },{ field : study_event_uuid , old :null, new : a413a027-0c30-4ffc-838a-0c75b48d15f7 },{ field : study_activity_uuid , old :null, new : 760c2680-0fa3-4fd3-854e-c1ec8e028346 },{ field : schedule_uuid , old :null, new : 4c6f0a79-4f08-42f2-99d8-209e0822b2e6 },{ field : created_at , old :null, new : 2016-02-02 14:15:34 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:15:34 UTC },{ field : required_minimum_quantity , old :null, new :1.0},{ field : optional_quantity , old :null, new :0},{ field : percentage_of_subjects , old :null, new :100.0},{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : lock_version , old :null, new :0}]}, why :{}, when_audited : 2016-02-02T14:15:34.458Z , when_persisted : 2016-02-02T14:20:07.626Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");                                                
			data[1][0] =  new String("{ audit_uuid : 5cd665f0-43f9-4541-9458-639f0a7f4571 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:a61fb567-b863-43a8-ad36-9d5b5b455d7d , what_uri : com:mdsol:schedules:3dce122e-77b0-4b16-93e3-7083073701c5 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : 3dce122e-77b0-4b16-93e3-7083073701c5 },{ field : name , old :null, new : United States - Standard Arm },{ field : study_uuid , old :null, new : effb8778-6bac-4d9d-8380-b3e638bc86dc },{ field : phase_uuid , old :null, new : b4e3fa0c-54cc-4749-a589-7d8808a34453 },{ field : indication_uuid , old :null, new : 84c72163-66fc-11e1-b86c-0800200c9a66 },{ field : picas_build_tag_id , old :null, new : 57 },{ field : calculation_flag , old :null, new : true },{ field : author_uuid , old :null, new : a61fb567-b863-43a8-ad36-9d5b5b455d7d },{ field : state , old :null, new : public },{ field : created_at , old :null, new : 2016-02-02 14:23:54 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:23:54 UTC },{ field : country_uuid , old :null, new : 9301b148-6703-11e1-b86c-0800200c9a66 },{ field : lock_version , old :null, new :0},{ field : overhead_pct , old :null, new :25.0},{ field : parent_schedule_uuid , old :null, new :null},{ field : arm_uuid , old :null, new :null},{ field : study_stage , old :null, new : contracting_sponsor }]}, why :{}, when_audited : 2016-02-02T14:23:54.604Z , when_persisted : 2016-02-02T14:25:07.732Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");                                                       
			data[2][0] = new String("{ audit_uuid : 94e786d9-a128-4945-aff0-e76b1f827616 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_cells:7b311f7e-96df-4f52-b553-43dc55f71bd4 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : 7b311f7e-96df-4f52-b553-43dc55f71bd4 },{ field : study_event_uuid , old :null, new : 490a1c19-093f-4a80-b94c-a05ea24faff5 },{ field : study_activity_uuid , old :null, new : d78809c2-bfa4-4d0c-b6b3-f680f37b0c6e },{ field : schedule_uuid , old :null, new : 18b665b4-1683-4009-ad72-666ab59f1d9d },{ field : created_at , old :null, new : 2016-02-02 14:35:20 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:35:20 UTC },{ field : required_minimum_quantity , old :null, new :1.0},{ field : optional_quantity , old :null, new :0},{ field : percentage_of_subjects , old :null, new :100.0},{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : lock_version , old :null, new :0}]}, why :{}, when_audited : 2016-02-02T14:35:20.666Z , when_persisted : 2016-02-02T14:40:08.282Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[3][0] = new String("{ audit_uuid : 698dd583-b078-49b4-9b3b-4187a11aa907 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_activities:c7a69b6e-5f0a-474a-b91d-694dce647b7d , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : c7a69b6e-5f0a-474a-b91d-694dce647b7d },{ field : schedule_uuid , old :null, new : 18b665b4-1683-4009-ad72-666ab59f1d9d },{ field : activity_uuid , old :null, new : aecb815c-6707-11e1-b86c-0800200c9a66 },{ field : base_study_activity_uuid , old :null, new : 721865cc-5175-40a2-8fc2-297852b100c8 },{ field : sequence_num , old :null, new :7864320},{ field : unit_cost_low , old :null, new :null},{ field : unit_cost_medium , old :null, new :null},{ field : unit_cost_high , old :null, new :null},{ field : complexity_val , old :null, new :null},{ field : alias_short_desc , old :null, new :null},{ field : optional_conditional_qualifier_uuid , old :null, new :null},{ field : percentage_of_subjects , old :null, new :null},{ field : created_at , old :null, new : 2016-02-02 14:35:18 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:35:18 UTC },{ field : lock_version , old :null, new :0},{ field : collection_activity_uuid , old :null, new :null},{ field : collection_activity_low_cost , old :null, new :0.0},{ field : collection_activity_medium_cost , old :null, new :0.0},{ field : collection_activity_high_cost , old :null, new :0.0},{ field : is_central_lab , old :null, new : false },{ field : analyte_quantity , old :null, new :1},{ field : overhead_flag , old :null, new : false },{ field : activity_type , old :null, new :1},{ field : custom_flag , old :null, new : false }]}, why :{}, when_audited : 2016-02-02T14:35:18.085Z,when_persisted : 2016-02-02T14:40:08.282Z ,corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[4][0] = new String("{ audit_uuid : 40db1c20-1cce-4e52-a5ee-d7f538e78413 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_cells:cb553467-8f5e-42a8-9724-53cbfdccca7b , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : cb553467-8f5e-42a8-9724-53cbfdccca7b },{ field : study_event_uuid , old :null, new : aa8c8b74-fa8a-45d8-8d60-98005206260a },{ field : study_activity_uuid , old :null, new : 991c2873-2ff1-432a-b3b8-d0ac40aa9564 },{ field : schedule_uuid , old :null, new : 968557c3-663e-40b7-8b97-b1917765a31b },{ field : created_at , old :null, new : 2016-02-02 14:36:15 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:36:15 UTC },{ field : required_minimum_quantity , old :null, new :1.0},{ field : optional_quantity , old :null, new :0},{ field : percentage_of_subjects , old :null, new :100.0},{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : lock_version , old :null, new :0}]}, why :{}, when_audited : 2016-02-02T14:36:15.199Z , when_persisted : 2016-02-02T14:40:08.282Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[5][0] = new String("{ audit_uuid : 7414b01d-82c4-4242-b9ac-7a1ec20476e0 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_cells:8e43d626-17d2-413b-80de-911afe4f296d , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : 8e43d626-17d2-413b-80de-911afe4f296d },{ field : study_event_uuid , old :null, new : dc9883d3-39a0-4c7e-b85f-7002ce6bd761 },{ field : study_activity_uuid , old :null, new : fe80b41e-63da-45b3-8ff8-58e4220df306 },{ field : schedule_uuid , old :null, new : 968557c3-663e-40b7-8b97-b1917765a31b },{ field : created_at , old :null, new : 2016-02-02 14:36:13 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:36:13 UTC },{ field : required_minimum_quantity , old :null, new :1.0},{ field : optional_quantity , old :null, new :0},{ field : percentage_of_subjects , old :null, new :100.0},{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : lock_version , old :null, new :0}]}, why :{}, when_audited : 2016-02-02T14:36:13.846Z , when_persisted : 2016-02-02T14:40:08.282Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[6][0] = new String("{ audit_uuid : aaa4a15a-b7da-479d-886f-ee3cd133b5f8 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_cells:6c398a7f-274f-4b78-9abf-035d69135017 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : 6c398a7f-274f-4b78-9abf-035d69135017 },{ field : study_event_uuid , old :null, new : 855b5227-b2bc-4ff5-967b-bb965d022317 },{ field : study_activity_uuid , old :null, new : 5272973a-87a7-42a0-abd1-e7e188262772 },{ field : schedule_uuid , old :null, new : 7db8a7d6-825f-447d-9199-d3fb298f6b7a },{ field : created_at , old :null, new : 2016-02-02 14:36:56 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:36:56 UTC },{ field : required_minimum_quantity , old :null, new :1.0},{ field : optional_quantity , old :null, new :0},{ field : percentage_of_subjects , old :null, new :100.0},{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : lock_version , old :null, new :0}]}, why :{}, when_audited : 2016-02-02T14:36:56.681Z , when_persisted : 2016-02-02T14:40:08.282Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[7][0] = new String("{ audit_uuid : e2200d45-00ce-4d63-b21f-88ce16717f4a , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_cells:06057e02-f11a-460e-86f7-a4a06bc48020 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : 06057e02-f11a-460e-86f7-a4a06bc48020 },{ field : study_event_uuid , old :null, new : 5e721cca-fe54-4525-b515-409da278370c },{ field : study_activity_uuid , old :null, new : 0c4f4209-87dc-4de0-8898-277d28975e42 },{ field : schedule_uuid , old :null, new : 7db8a7d6-825f-447d-9199-d3fb298f6b7a },{ field : created_at , old :null, new : 2016-02-02 14:36:57 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:36:57 UTC },{ field : required_minimum_quantity , old :null, new :1.0},{ field : optional_quantity , old :null, new :0},{ field : percentage_of_subjects , old :null, new :100.0},{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : lock_version , old :null, new :0}]}, why :{}, when_audited : 2016-02-02T14:36:57.172Z , when_persisted : 2016-02-02T14:40:08.282Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[8][0] = new String("{ audit_uuid : db0dae08-5873-4544-99f1-dcdffcf84ea6 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_events:cad824aa-e926-45bb-82cc-c3565dad62b0 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : cad824aa-e926-45bb-82cc-c3565dad62b0 },{ field : schedule_uuid , old :null, new : 7db8a7d6-825f-447d-9199-d3fb298f6b7a },{ field : base_study_event_uuid , old :null, new : cad824aa-e926-45bb-82cc-c3565dad62b0 },{ field : name , old :null, new : visit 8 },{ field : sequence_num , old :null, new :8323072},{ field : visit_type , old :null, new : visit_type.treatment },{ field : encounter_type , old :null, new : encounter_type.visit_outpatient },{ field : duration , old :null, new :null},{ field : duration_unit , old :null, new :null},{ field : min_repeats , old :null, new :null},{ field : expected_repeats , old :null, new :null},{ field : max_repeats , old :null, new :null},{ field : repeat_unit , old :null, new :null},{ field : study_days , old :null, new :null},{ field : created_at , old :null, new : 2016-02-02 14:36:56 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:36:56 UTC },{ field : optional_conditional_qualifier_id , old :null, new :1},{ field : percentage_of_subjects , old :null, new :100.0},{ field : lock_version , old :null, new :0},{ field : overhead_flag , old :null, new : false }]}, why :{}, when_audited : 2016-02-02T14:36:56.502Z , when_persisted : 2016-02-02T14:40:08.282Z , corrected_audit_uuid :null, tags :null, valid_audit :true}");
			data[9][0] = new String("{ audit_uuid : 96280eb4-efdb-498d-bbf4-53767a8c5d52 , where_uri : com:mdsol:apps:4aaf4ac1-b0fd-45c2-8b8f-88aaf3a6248f , who_uri : com:mdsol:users:15b7c383-c454-4151-9a0d-0bf17137f480 , what_uri : com:mdsol:study_activities:0c4f4209-87dc-4de0-8898-277d28975e42 , which_changed :{ type : create , changes :[{ field : uuid , old :null, new : 0c4f4209-87dc-4de0-8898-277d28975e42 },{ field : schedule_uuid , old :null, new : 7db8a7d6-825f-447d-9199-d3fb298f6b7a },{ field : activity_uuid , old :null, new : 99e5312e-6704-11e1-b86c-0800200c9a66 },{ field : base_study_activity_uuid , old :null, new : da91e7eb-e595-4e5b-b41e-a465fdbb6d8e },{ field : sequence_num , old :null, new :8323072},{ field : unit_cost_low , old :null, new :null},{ field : unit_cost_medium , old :null, new :null},{ field : unit_cost_high , old :null, new :null},{ field : complexity_val , old :null, new :null},{ field : alias_short_desc , old :null, new :null},{ field : optional_conditional_qualifier_uuid , old :null, new :null},{ field : percentage_of_subjects , old :null, new :null},{ field : created_at , old :null, new : 2016-02-02 14:36:55 UTC },{ field : updated_at , old :null, new : 2016-02-02 14:36:55 UTC },{ field : lock_version , old :null, new :0},{ field : collection_activity_uuid , old :null, new : 99e5583d-6704-11e1-b86c-0800200c9a66 },{ field : collection_activity_low_cost , old :null, new :0.0},{ field : collection_activity_medium_cost , old :null, new :0.0},{ field : collection_activity_high_cost , old :null, new :0.0},{ field : is_central_lab , old :null, new : false },{ field : analyte_quantity , old :null, new :1},{ field : overhead_flag , old :null, new : false },{ field : activity_type , old :null, new :1},{ field : custom_flag , old :null, new : false }]}, why :{}, when_audited : 2016-02-02T14:36:55.717Z , when_persisted : 2016-02-02T14:40:08.282Z ,corrected_audit_uuid :null, tags :null, valid_audit :true}");

		}

		//configure the model with the data and column headers
		dm.setDataVector(data, columnNames);
		
		// Connect the model to the table
		table.setModel(dm);

	    //Add action listeners to the buttons
		btnLoadaudits.addActionListener(this);

	    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	    frame.getContentPane().add(scrollPane);

		frame.setSize(700, 500);
		frame.setVisible(true);
	  }


	  //create the action listener
	   public void actionPerformed(ActionEvent e){
		   	//check which button was clicked
	         if (e.getActionCommand().equals("LoadAudits")){
			              System.out.println("Load Audits called");
			             }
			//force gui udpate
			table.invalidate();
			frame.invalidate();
			frame.validate();
	        frame.repaint();	
		
	}
}
