/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;
/////////////////////////////////////////////////
import a1.annotations.Forms;
import a1.entity.Contacts;
import a1.entity.Events;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.mail.util.MailSSLSocketFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
import com.toedter.calendar.IDateEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.jdesktop.swingx.JXTreeTable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.ws.rs.core.MultivaluedMap;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.jdesktop.swingx.JXEditorPane;
import org.jdesktop.swingx.JXTable;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author nnn
 */
public class Main extends javax.swing.JFrame {

	// public Session session;
	static SessionFactory Factory;// =
									// HibernateUtil.getSessionFactory("jdbc:mysql://localhost:3306/psk2?zeroDateTimeBehavior=convertToNull");
	// SessionFactory Factory = HibernateUtil.getSessionFactory();

	static public Session session;// = Factory.openSession();

	static Properties mailServerProperties;
	static javax.mail.Session getMailSession;
	static MimeMessage generateMailMessage;

	HashMap CCmap;

	TableRowSorter sorter;

	int DayWidth = 80;
	int TimeHeight = 22;
	int x_offset = 20;
	int y_offset = 25;
	int time_start = 8 * 2;
	int time_end = 23 * 2 + 1;
	int time_count = time_end - time_start;
	LocalDate aStart;
	LocalDate aEnd;
	public static Properties props;
	String lasturl = "";

	/**
	 * Creates new form Main
	 */
	public Main() {
		initComponents();
	}

	public static boolean save(Object o) {
		// Session session = ((Main) Owner).session;
		try {
			session.beginTransaction();
			session.save(o);
			session.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			generateAndSendEmail(ex);
			return false;
		}
	}

	public static List SQLQuery(String sq, List args, Class T) {
		session.beginTransaction();
		Query q = session.createSQLQuery(sq)
				.addEntity(T);
		session.getTransaction().commit();
		return q.list();
	}

	public static Object FindByField(String type, String f, Object v) {
		try {
			session.beginTransaction();

			BeanInfo info = Introspector.getBeanInfo(Class.forName("a1.entity." + type));
			Class t = null;
			SqlTypeDescriptor tt = null;
			for (PropertyDescriptor p : info.getPropertyDescriptors()) {
				if (p.getName().equals(f)) {
					t = p.getPropertyType();
					break;
				}
			}
			Object vv = v;
			if (v.getClass() == Integer.class)
				vv = ((Integer) v).intValue();
			if (t == String.class && v.getClass() == int.class)
				vv = String.valueOf(v);
			if (t == int.class && v.getClass() == String.class)
				vv = Integer.parseInt((String) v);

			List q = session.createQuery("from " + type + " where " + f + "=:par").setParameter("par", vv,
					((t == int.class || t == Integer.class) ? Hibernate.INTEGER : Hibernate.STRING)).list();
			Iterator it = q.iterator();
			session.getTransaction().commit();
			if (it.hasNext()) {
				return it.next();
			} else {
				return null;
			}
		} catch (IntrospectionException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}
		return null;
	}
	public int calendar_state = 1;

	private java.util.List my_runQuery(String query1) {

		try {
			session.beginTransaction();
			Query q = session.createQuery(query1);
			session.getTransaction().commit();
	
			return q.list();
			}
			catch (Exception ex) {
				generateAndSendEmail(ex);
				return null;
			}
	}

	private void initComponents() {
		try {
			initconnection();////////////////////////////////////
		} catch (Exception ex) {
			generateAndSendEmail(ex);
			// e.printStackTrace();
		}
		jInternalFrame1 = new javax.swing.JInternalFrame();
		buttonGroup1 = new javax.swing.ButtonGroup();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLayeredPane1 = new javax.swing.JLayeredPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		// jTextArea1 = new javax.swing.JTextArea();
		jCalendar4 = new com.toedter.calendar.JCalendar();
		queryButton1 = new javax.swing.JButton();
		jDateChooser1 = new com.toedter.calendar.JDateChooser();
		jDateChooser2 = new com.toedter.calendar.JDateChooser();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jPanel5 = new javax.swing.JPanel();
		jScrollPane10 = new javax.swing.JScrollPane();
		ContactTable = new JXTreeTable();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();

		jInternalFrame1.setVisible(true);

		javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
		jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
		jInternalFrame1Layout.setHorizontalGroup(jInternalFrame1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("QuickDev");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}

			public void windowOpened(java.awt.event.WindowEvent evt) {
				formWindowOpened(evt);
			}
		});

		jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jTabbedPane1FocusGained(evt);
			}
		});

		javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
		jLayeredPane1.setLayout(jLayeredPane1Layout);
		jLayeredPane1Layout
				.setHorizontalGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jLayeredPane1Layout.createSequentialGroup().addGap(300, 300, 300)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(500, Short.MAX_VALUE)));
		jLayeredPane1Layout.setVerticalGroup(
				jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jLayeredPane1Layout.createSequentialGroup().addContainerGap(275, Short.MAX_VALUE)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(114, 114, 114)));
		jLayeredPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jScrollPane1.setViewportView(jLayeredPane1);

		jCalendar4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
		jCalendar4.setMinimumSize(new java.awt.Dimension(100, 118));
		jCalendar4.setPreferredSize(new java.awt.Dimension(130, 153));
		jCalendar4.setWeekOfYearVisible(false);
		
		jCalendar4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				jCalendar4PropertyChange(evt);
			}
		});

		queryButton1.setText("Обновить");
		queryButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				queryButton1ActionPerformed(evt);
			}
		});

		jDateChooser1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jDateChooser1FocusGained(evt);
			}
		});
		IDateEditor editor = jDateChooser1.getDateEditor();
		JComponent comp = editor.getUiComponent();
		comp.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jDateChooser1FocusGained(evt);
			}
		});

		jDateChooser2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jDateChooser2FocusGained(evt);
			}
		});
		IDateEditor editor2 = jDateChooser2.getDateEditor();
		JComponent comp2 = editor2.getUiComponent();
		comp2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jDateChooser2FocusGained(evt);
			}
		});

		jLabel1.setText("с:");

		jLabel2.setText("по:");

		jLabel3.setText("Период");

		jTabbedPane1.addTab("Ежедневник", jPanel4);
		GroupLayout gl_jPanel4 = new GroupLayout(jPanel4);
		gl_jPanel4
				.setHorizontalGroup(gl_jPanel4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_jPanel4.createSequentialGroup().addGap(10)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE).addGap(18)
								.addGroup(gl_jPanel4.createParallelGroup(Alignment.LEADING)
										.addComponent(jCalendar4, GroupLayout.PREFERRED_SIZE, 291,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3)
								.addGroup(gl_jPanel4.createSequentialGroup().addComponent(jLabel1).addGap(4)
										.addComponent(jDateChooser1, GroupLayout.PREFERRED_SIZE, 136,
												GroupLayout.PREFERRED_SIZE)
										.addGap(4).addComponent(jLabel2).addGap(4).addComponent(jDateChooser2,
												GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
								.addComponent(queryButton1)).addGap(6)));
		gl_jPanel4.setVerticalGroup(gl_jPanel4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jPanel4.createSequentialGroup().addGap(11)
						.addGroup(gl_jPanel4.createParallelGroup(Alignment.LEADING)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
								.addGroup(gl_jPanel4.createSequentialGroup()
										.addComponent(jCalendar4, GroupLayout.PREFERRED_SIZE, 218,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(jLabel3).addGap(6)
										.addGroup(gl_jPanel4.createParallelGroup(Alignment.LEADING)
												.addComponent(jLabel1)
												.addComponent(jDateChooser1, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel2).addComponent(jDateChooser2, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(6).addComponent(queryButton1)))
						.addGap(11)));
		jPanel4.setLayout(gl_jPanel4);
		setVisible(false);

		setVisible(false);

		setVisible(false);

//		ContactTable
//				.setModel(new javax.swing.table.DefaultTableModel(
//						new Object[][] { { null, null, null, null }, { null, null, null, null },
//								{ null, null, null, null }, { null, null, null, null } },
//						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane10.setViewportView(ContactTable);
		
		Contacts_toolBar = new JToolBar();

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5Layout.setHorizontalGroup(
			jPanel5Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane10, GroupLayout.DEFAULT_SIZE, 1098, Short.MAX_VALUE)
						.addComponent(Contacts_toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		jPanel5Layout.setVerticalGroup(
			jPanel5Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
					.addGap(9)
					.addComponent(Contacts_toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(jScrollPane10, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
					.addContainerGap())
		);
		jPanel5.setLayout(jPanel5Layout);

		jTabbedPane1.addTab("Контакты", jPanel5);

		jMenu1.setText("Главная");

		jMenuItem1.setText("Создать профиль");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setText("Открыть профиль");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuItem3.setText("Сохранить профиль как");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem3);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane1).addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup()
										.addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}
	
	private void update() {
		jLayeredPane1.removeAll();

		LocalDateTime ldt = LocalDateTime.of(1, 1, 1, 1, 1);

		if (null == jDateChooser1.getDate()) {
			if (calendar_state == 0) {
				jDateChooser1.setDate(Date.from(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1)
						.atStartOfDay().toInstant(ZoneOffset.UTC)));
			}
			if (calendar_state == 1) {
				LocalDate ld = LocalDate.now();
				// ld =ld.minusMonths(1);
				jDateChooser1.setDate(Date
						.from(ld.plusDays(1 - ld.getDayOfWeek().getValue()).atStartOfDay().toInstant(ZoneOffset.UTC)));
			}
			;
			if (calendar_state == 2) {
				jDateChooser1.setDate(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
			}
		}
		;
		if (null == jDateChooser2.getDate()) {
			if (calendar_state == 0) {
				LocalDate ld = LocalDate.now();
				ld = ld.plusMonths(1);
				jDateChooser2.setDate(Date.from(LocalDate.of(ld.getYear(), ld.getMonth(), 1).minusDays(1).atStartOfDay()
						.toInstant(ZoneOffset.UTC)));
			}
			;
			if (calendar_state == 1) {
				LocalDate ld = LocalDate.now();
				jDateChooser2.setDate(Date
						.from(ld.plusDays(7 - ld.getDayOfWeek().getValue()).atStartOfDay().toInstant(ZoneOffset.UTC)));
			}
			;
			if (calendar_state == 2) {
				jDateChooser2.setDate(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
			}
		}
		;
		// *********************************************************************************************
		LocalDate a1 = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		aStart = a1;
		LocalDate a2 = jDateChooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		aEnd = a2;

		jLayeredPane1.setLayout(null);
		jLayeredPane1.setBounds(1, 1, 10000, 10000);
		jLayeredPane1.setPreferredSize(new Dimension(10000, y_offset + TimeHeight * (time_count + 2) + 100));
		int i = 1;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YY");
		while (a1.isBefore(a2)) {
			JTextArea NewjTextArea = new javax.swing.JTextArea();
			NewjTextArea.setBackground(new java.awt.Color(0x00ffffff, true));
			NewjTextArea.setColumns(20);
			NewjTextArea.setEditable(false);
			NewjTextArea.setRows(5);
			NewjTextArea.setText(dateTimeFormatter.format(a1).toString());
			jLayeredPane1.add(NewjTextArea);
			NewjTextArea.setBounds(x_offset + i * DayWidth, // y_offset +
					1, DayWidth, y_offset + TimeHeight * (time_count));// setSize(80,
																		// 30);
			// NewjTextArea.setLocation(20,20);

			a1 = a1.plusDays(1);
			i++;
		}
		int max_x = i;
		String my_headers[] = { "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00",
				"04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
				"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00",
				"15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30",
				"21:00", "21:30", "22:00", "22:30", "23:00", "23:30" };

		for (i = time_start; i < time_end; i++) {
			JTextArea NewjTextArea = new javax.swing.JTextArea();
			NewjTextArea.setBackground(new java.awt.Color(255, 255, 255));
			NewjTextArea.setColumns(20);
			NewjTextArea.setRows(5);
			NewjTextArea.setEditable(false);
			NewjTextArea.setText(my_headers[i]);
			jLayeredPane1.add(NewjTextArea);
			NewjTextArea.setBounds(1, y_offset + (i // + 1
					- time_start) * TimeHeight, x_offset + DayWidth * max_x, TimeHeight);
		}
		;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		java.util.ArrayList result = (ArrayList) SQLQuery("select * from events where (start >='" + df.format(aStart)
				+ "' and start >=" + df.format(aEnd) + ") or start ='0001-01-01'", null, Events.class); 
		for (int k = 0; k < result.size(); k++) {
			if (((Events) result.get(k)).getStart() == null) {
				continue;
			}
			LocalDateTime d1 = ((Events) result.get(k)).getStart_ldt();
			LocalDateTime d2;
			if (((Events) result.get(k)).getEnd() == null) {
				d2 = ((Events) result.get(k)).getStart_ldt().plusMinutes(30);
			} else if (d1.isAfter(((Events) result.get(k)).getEnd_ldt())) {
				d2 = ((Events) result.get(k)).getStart_ldt().plusMinutes(30);
			} else {
				d2 = ((Events) result.get(k)).getEnd_ldt();
			}
			;
			if (d1.isAfter(aStart.atStartOfDay()) || d1.isEqual(aStart.atStartOfDay())
					&& (d1.isBefore(aEnd.atTime(23, 59, 59)) || d1.isEqual(aEnd.atTime(23, 59, 59)))) {
				vEvent NewjTextArea = new vEvent();// javax.swing.JTextArea();
				NewjTextArea.event = (Events) result.get(k);
				NewjTextArea.setEditable(false);
				NewjTextArea.setBackground(new java.awt.Color((int) ((Events) result.get(k)).getType().getColor()// 0xdd8888ff
				, true));// new java.awt.Color(101, 101, 255));
				NewjTextArea.setColumns(20);
				NewjTextArea.setRows(5);
				NewjTextArea.setText(((Events) result.get(k)).getName() + " "
						+ ((Events) result.get(k)).getStart_ldt().toLocalTime().toString());
				NewjTextArea.setLineWrap(true);
				NewjTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
					@Override
					public void mousePressed(java.awt.event.MouseEvent evt) {
						MyMousePressed(evt);
					}

					@Override
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						EventClicked(evt);
					}
				});
				NewjTextArea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					@Override
					public void mouseDragged(java.awt.event.MouseEvent evt) {
						MyMouseDragged(evt);
					}
				});
				NewjTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
					@Override
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						MyMouseReleased(evt);
					}
				});
				jLayeredPane1.add(NewjTextArea);

				Period period = Period.between(aStart, d1.toLocalDate());

				int event_offset_x = (period.getDays() + 1) * DayWidth;
				int event_offset_y = ((d1.toLocalTime().getHour() * 60 + d1.toLocalTime().getMinute()) / 30 - time_start // +
																															// 1
				) * TimeHeight;
				NewjTextArea.setBounds(x_offset + event_offset_x, y_offset + event_offset_y, DayWidth,
						(int) ChronoUnit.MINUTES.between(d1, d2) / 30 * TimeHeight);
				jLayeredPane1.moveToFront(NewjTextArea);
			}
			;

		}
		;
	};


	void EventClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() > 1) {
			// System.out.println("!!!!!!");
			VEvent VEv1 = new VEvent();
			// VEv1.Owner = this;
			VEv1.setVisible(true);
			VEv1.toFront();
			VEv1.event = ((vEvent) evt.getSource()).event;
		}
	};

	private void formWindowOpened(java.awt.event.WindowEvent evt) {
		reload_data();
	}

	public void initconnection() throws Exception {
		if (props == null)
			try {
				load_properties();
			} catch (IOException ex) {
				generateAndSendEmail(ex);
			}

		if (!lasturl.equals(props.getProperty("lastBase"))) {
			Factory = HibernateUtil.getSessionFactory(props.getProperty("lastBase"), getLogin(), getPassword());// "jdbc:mysql://localhost:3306/psk2?zeroDateTimeBehavior=convertToNull");
			session = Factory.openSession();
			session.beginTransaction();
			org.hibernate.SQLQuery q = session.createSQLQuery("select  * from contacts limit 1")
					.addEntity(Contacts.class);
			session.getTransaction().commit();
			lasturl = props.getProperty("lastBase");
		}

	}

	public void reload_data() {
		try {
			initconnection();
		} catch (Exception ex) {
			generateAndSendEmail(ex);
			return;
		}
		update();
		update_contacts();
	}

	public void update_contacts() {
		ContactTable.setTreeTableModel(new BeanTreeTableModel("select * from contacts", null, Contacts.class, new ArrayNode(new Object[0])));
		ArrayList<HashMap> tt = new ArrayList<>();
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "id", "№", 60))));
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "f", "Фамилия", 200))));
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "i", "Имя", 200))));
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "o", "Отчество", 200))));
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "status", "Статус", 200))));
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "address", "Адрес", 200))));
		tt.add(ut.newHashMap(new ArrayList<Object>(Arrays.asList("name,title,width", "phone", "Телефон", 200))));
		ut.TuneTreeColumns(ContactTable, tt);		
		ut.TuneToolbar(Contacts_toolBar, ContactTable);
	}

	public int getEntityId(JTable t, int r) {
		TableColumnModel tcm = t.getColumnModel();
		int id_col = -1;
		for (TableColumn c : Collections.list(tcm.getColumns())) {
			if (c.getIdentifier().equals("id")) {
				id_col = c.getModelIndex();
				break;
			}
		}
		return id_col == -1 ? -1 : (int) t.getModel().getValueAt(t.convertRowIndexToModel(r), id_col);
	}


	public void load_properties() throws IOException {

		File currentDir = new File(".");
		String sDirSeparator = System.getProperty("file.separator");
		String sFileName = "my.properties";
		String sFilePath = currentDir.getCanonicalPath() + sDirSeparator + sFileName;
		if (props == null) {
			props = new Properties();
		}
		try {
			FileInputStream ins = new FileInputStream(sFilePath);
			props.load(ins);
		} catch (Exception ex) {
			generateAndSendEmail(ex);
		} finally {
			if (!props.containsKey("name")) {
				int rand = new Random().nextInt();
				rand = rand < 0 ? -rand : rand;
				String temp = String.valueOf(rand);
				props.put("name", temp);
				props.put("id", temp);
				save_properties();
			};
			if (!props.containsKey("languge")) {
				props.put("languge", "");
			}
			if (!props.containsKey("bases")) {
				save_properties();
				create_profile();
			}
		}
		;
	}

	public void save_properties() throws IOException {

		File currentDir = new File(".");
		String sDirSeparator = System.getProperty("file.separator");
		String sFileName = "my.properties";
		String sFilePath = currentDir.getCanonicalPath() + sDirSeparator + sFileName;
		FileOutputStream ins = new FileOutputStream(sFilePath);
		props.store(ins, "");
	}

	public static String convertToXML(Object ml) {
		XStream xstream = new XStream(new DomDriver());
		return xstream.toXML(ml);
	}

	public static Object convertFromXML(String XMLString) {
		Object ml = null;
		XStream xstream = new XStream(new DomDriver());
		Object obj = xstream.fromXML(XMLString);
		if (obj instanceof Object) {
			ml = (Object) obj;
		}
		return ml;
	}

	private void queryButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_queryButton1ActionPerformed
		update();
	}

	@SuppressWarnings("empty-statement")
	private void jCalendar4PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendar4PropertyChange
		try {
			if (evt.getOldValue()==null) return;
			if (!evt.getOldValue().equals(evt.getNewValue())) {
				if ("jDateChooser1".equals(LastFocus)) {
					jDateChooser1.setCalendar((Calendar) evt.getNewValue());
					LastFocus = "jDateChooser2";
				} else if ("jDateChooser2".equals(LastFocus)) {
					jDateChooser2.setCalendar((Calendar) evt.getNewValue());
					LastFocus = "jDateChooser1";
				};
			};
		} catch (Exception ex) {
			generateAndSendEmail(ex);
		};

	}

	private void jDateChooser1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jDateChooser1FocusGained
		LastFocus = "jDateChooser1";
	}

	private void jDateChooser2FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jDateChooser2FocusGained
		LastFocus = "jDateChooser2";
	}// GEN-LAST:event_jDateChooser2FocusGained

	private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTabbedPane1FocusGained
		if (this.jTabbedPane1.getSelectedIndex() == 3) {
			System.out.println("3333333333333");
		}

	}

	private void CasesTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_CasesTableMouseClicked
		if (evt.getClickCount() == 2) {
			System.out.println("11111111");

		}
	}


	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		try {
			save_properties();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}
	}

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem3ActionPerformed
		try {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("sql dumps", "sql");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getAbsolutePath();
				if (!path.substring(path.length() - 4).equals(".sql")) {
					path = path + ".sql";
				}
				Process exec = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c",
						"mysqldump "+(getPassword().equals("")==true?"":("-p" + getPassword())) + " -u " + getLogin() + " " + getBaseName() + " > " + path });
				if (exec.waitFor() == 0) {
					InputStream inputStream = exec.getInputStream();
					byte[] buffer = new byte[inputStream.available()];
					inputStream.read(buffer);

					String str = new String(buffer);
					System.out.println(str);
					FileOutputStream fileOutputStream = new FileOutputStream(path, true);
					BufferedWriter bufferedWriter = new BufferedWriter(
							new OutputStreamWriter(fileOutputStream, "UTF-8"));
					try {
						bufferedWriter.append("-- " + props.getProperty("id") + "\n");
						bufferedWriter.append("-- " + props.getProperty("name"));
						bufferedWriter.flush();
					} finally {
						bufferedWriter.close();

						try {
							fileOutputStream.close();
						} catch (IOException ex) {
							generateAndSendEmail(ex);
						}
					}
				} else {
					InputStream errorStream = exec.getErrorStream();
					byte[] buffer = new byte[errorStream.available()];
					errorStream.read(buffer);

					String str = new String(buffer);
					System.out.println(str);

				}
			}
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}

	}// GEN-LAST:event_jMenuItem3ActionPerformed

	public String getBaseName() {
		String a = props.getProperty("lastBase");
		int end = a.indexOf("?");
		int start = a.lastIndexOf("/", end);
		return a.substring(start + 1, end);
	}

	public String getLogin() {
		if (props.getProperty("login") == null)
			return "root";
		return props.getProperty("login").equals("") ? "root" : props.getProperty("login");
	}

	public String getPassword() {
		if (props.getProperty("password") == null)
			return "";
		return props.getProperty("password").equals("") ? "" : props.getProperty("password");
	}

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
		JDialog fr = new BasesDialog(this, true);
		// fr.own //main =this;
		fr.setVisible(true);
		fr.toFront();
	}// GEN-LAST:event_jMenuItem2ActionPerformed

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed

		create_profile();
		reload_data();

	}// GEN-LAST:event_jMenuItem1ActionPerformed

	public void create_profile() {
		try {

			String post_text = JOptionPane.showInputDialog(null, "Создание профиля", "Введите ФИО",
					JOptionPane.PLAIN_MESSAGE);
			if (post_text == null) {
				return;
			}

			props.setProperty("name", post_text);
			int rand = new Random().nextInt();
			rand = rand < 0 ? -rand : rand;
			String temp = String.valueOf(rand);
			props.setProperty("id", temp);
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=" + getLogin()
						+ "&password=" + getPassword() + "&zeroDateTimeBehavior=convertToNull");
				java.sql.Statement st = con.createStatement();
				st.executeUpdate("CREATE DATABASE a" + props.getProperty("id"));
				st.close();
			} catch (SQLException ex) {
				System.out.println("Can't create database");
				generateAndSendEmail(ex);
				return;
			} finally {
				// st.close();
			}
			;
			Process exec = Runtime.getRuntime().exec(new String[] { "mysql", "a" + props.getProperty("id"),
					"-u" + getLogin(), getPassword().equals("") ? "" : "-p" + getPassword(), "-e", " source new.sql" });
			if (exec.waitFor() == 0) {
				InputStream inputStream = exec.getInputStream();
				byte[] buffer = new byte[inputStream.available()];
				inputStream.read(buffer);

				String str = new String(buffer);
				System.out.println(str);
				ArrayList<HashMap> a = null;
				if (props.getProperty("bases") == null)
					a = new ArrayList<HashMap>();
				else
					a = (ArrayList) convertFromXML(props.getProperty("bases"));
				HashMap hm = new HashMap();
				hm.put("name", (String) props.getProperty("name"));
				hm.put("id", (String) props.getProperty("id"));
				hm.put("base", "jdbc:mysql://localhost:3306/a" + (String) props.getProperty("id")
						+ "?zeroDateTimeBehavior=convertToNull");
				a.add(hm);
				props.put("bases", convertToXML(a));
				props.put("lastBase", hm.get("base"));
				save_properties();

			} else {
				// abnormally terminated, there was some problem
				// a way to read the error during the execution of the command
				InputStream errorStream = exec.getErrorStream();
				byte[] buffer = new byte[errorStream.available()];
				errorStream.read(buffer);

				String str = new String(buffer);
				System.out.println(str);

			}

		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}
	}


	private void MyMouseDragged(java.awt.event.MouseEvent evt) {
		Point new_p = evt.getPoint();
		Component comp = evt.getComponent();// .getParent();
		Point p = comp.getLocation();
		comp.setLocation(new_p.x - (old.x - p.x), new_p.y - (old.y - p.y));
	}

	private void MyMouseReleased(java.awt.event.MouseEvent evt) {
		Events obj = ((vEvent) evt.getComponent()).event;

		long dx = (((vEvent) evt.getComponent()).getX() - x_offset) / DayWidth;
		long dy = (((vEvent) evt.getComponent()).getY() - y_offset) / TimeHeight;
		long l = Duration.between(obj.getStart_ldt(), obj.getEnd_ldt()).getSeconds();
		LocalTime t = LocalTime.ofSecondOfDay((time_start + dy) * 60 * 60 / 2);
		LocalDateTime NewLdt1 = LocalDateTime.of(aStart.plusDays(dx - 1), t);
		LocalDateTime NewLdt2 = LocalDateTime.of(aStart.plusDays(dx - 1), t.plusSeconds(l));
		obj.setStart_ldt(NewLdt1);
		obj.setEnd_ldt(NewLdt2);

		save(obj);

	}

	private void MyMousePressed(java.awt.event.MouseEvent evt) {
		old = evt.getPoint();
	}

	Point old;
	static String LastFocus = "jDateChooser1";

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}

		catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}

		catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}

		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			generateAndSendEmail(ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main().setVisible(true);
			}
		});
	}
	private JXTreeTable ContactTable;
	private javax.swing.ButtonGroup buttonGroup1;
	private com.toedter.calendar.JCalendar jCalendar4;
	private com.toedter.calendar.JDateChooser jDateChooser1;
	private com.toedter.calendar.JDateChooser jDateChooser2;
	private javax.swing.JInternalFrame jInternalFrame1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane10;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JButton queryButton1;
	private JScrollPane scrollPane10;
	private JXTreeTable material_treeTable;
	private JXTreeTable other_treeTable;
	private JToolBar Contacts_toolBar;

	class vEvent extends javax.swing.JTextArea {

		public Events event;
	};

	public class VEvents {

		public String Label;
		public Events Event;

		public VEvents(String L, Events E) {
			Label = L;
			Event = E;
		};

	}

	public static boolean delete(Object e) {
		try {
		session.beginTransaction();
		session.delete(e);
		session.getTransaction().commit();
		return true;
		} catch (Exception ex) {
			generateAndSendEmail(ex);
			return false;
		}
	}

	public static void generateAndSendEmail(Throwable ex) {
		String a = ex.getMessage();
		if(a==null) a = ex.toString();
		for (StackTraceElement ste : ex.getStackTrace())
			a=a+"<br>"+ste.toString();
		generateAndSendEmail(a);
	}
	public static void generateAndSendEmail(String emailBody) {

		try {
			getMailSession = createSmtpSession();//javax.mail.Session.getDefaultInstance(mailServerProperties, null);//(Authenticator)auth);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.setFrom(new InternetAddress("error@bizcons.tk"));
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("andreev.n.o@gmail.com"));
			generateMailMessage.setSubject("Greetings from Crunchify..");
			generateMailMessage.setContent(emailBody, "text/html");
			Transport transport = getMailSession.getTransport("smtp");
			transport.connect( "error@bizcons.tk", "AZb2cittl3");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			System.out.println("Problem"+ex.getMessage());
		}
		;
	}
	public static javax.mail.Session createSmtpSession() throws GeneralSecurityException {
		  final Properties props = new Properties();
		  MailSSLSocketFactory sf = new MailSSLSocketFactory();
		  sf.setTrustAllHosts(true);		  
		  props.put("mail.smtp.ssl.socketFactory", sf);
		  props.setProperty("mail.smtp.username", "error@bizcons.tk");
		  props.setProperty("mail.smtp.host", "78.24.221.38");
		  props.setProperty("mail.smtp.auth", "true");
		  props.setProperty("mail.smtp.port", "" + 587);
		  props.setProperty("mail.smtp.starttls.enable", "true");
		  props.setProperty("mail.transport.protocol", "smtp");

		  return javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {

		    protected PasswordAuthentication getPasswordAuthentication() {
		      return new PasswordAuthentication("error@bizcons.tk", "AZb2cittl3");
		    }
		  });
		}
}
