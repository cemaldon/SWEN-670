package org.aspectj.tools.ajde.netbeans;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

//~--- non-JDK imports --------------------------------------------------------
import org.aspectj.ajde.Ajde;

//import org.openide.windows.Workspace;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.util.actions.BooleanStateAction;
import org.openide.util.actions.CallableSystemAction;
import org.openide.util.actions.SystemAction;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

//~--- JDK imports ------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;

public class AJStartBrowserAction extends BooleanStateAction {

    @Override
    protected void firePropertyChange(String name, Object oldValue, Object newValue) {
        super.firePropertyChange(name, oldValue, newValue); //To change body of generated methods, choose Tools | Templates.
    }

    public JToggleButton getPresenter() {
        return presenter;
    }

   private boolean installed = false;
   private boolean firstInstall = true;
   private JToggleButton presenter = null;
   private Logger logger = Logger.getLogger(AJStartBrowserAction.class.getName());

   // public StructureViewTab structureViewTab = null;
   private StructureViewComponent structureViewTab = null;
   
   void setProperties(String propertyFileName, String [][] propertyValues)
   {
       try
       {
           FileOutputStream streamOut = new FileOutputStream(propertyFileName);
           
           for(String [] sarray : propertyValues)
           {
                Properties properties = new Properties();

                properties.setProperty(sarray[0], sarray[1]);

                properties.store(streamOut, "AJ Property");
           }
           
           streamOut.close();
       }
       
       catch(IOException ioe)
       {
          ioe.printStackTrace();
       }
   }
   
   void doProperties(Project project)
   {
      FileObject project_directory = project.getProjectDirectory();

      String project_directory_path = project_directory.getPath();

      String private_properties_path_and_name 
              = project_directory_path + "/nbproject/private/private.properties";

      String propertyValues[][] = {{"compile.on.save", "false"},
                                   {"do.depend", "false"},
                                   {"do.jar", "true"},
                                   {"javac.debug", "true"}};

      setProperties(private_properties_path_and_name,
                    propertyValues);
   }

   AJStartBrowserAction()
   {
      class AJProjectsPropertyChangeListener
        implements java.beans.PropertyChangeListener
      {
          public void propertyChange(java.beans.PropertyChangeEvent e) 
          {
            if(!firstInstall)
              if(installed)
              {
                  if(org.netbeans.api.project.ui.OpenProjects.getDefault().getOpenProjects().length == 0)
                  {
                        CallableSystemAction BUILD = (CallableSystemAction) SystemAction.get(AJBuildAction.class);
                        CallableSystemAction SELECT = (CallableSystemAction) SystemAction.get(AJSelectConfigurationAction.class);
                        CallableSystemAction CLEAN = (CallableSystemAction) SystemAction.get(AJCleanAction.class);
                        CallableSystemAction RUN = (CallableSystemAction) SystemAction.get(AJRunAction.class);
                        CallableSystemAction DEBUG = (CallableSystemAction) SystemAction.get(AJDebugAction.class);

                        BUILD.setEnabled(false);
                        SELECT.setEnabled(false);
                        RUN.setEnabled(false);
                        CLEAN.setEnabled(false);
                        DEBUG.setEnabled(false);

                        //installed = false;
                  }
                  else
                  {
                      Project project = OpenProjects.getDefault().getMainProject();
                      if(null != project)
                      {
                            doProperties(project);

                            CallableSystemAction BUILD = (CallableSystemAction) SystemAction.get(AJBuildAction.class);
                            CallableSystemAction SELECT = (CallableSystemAction) SystemAction.get(AJSelectConfigurationAction.class);
                            CallableSystemAction CLEAN = (CallableSystemAction) SystemAction.get(AJCleanAction.class);

                            BUILD.setEnabled(true);
                            SELECT.setEnabled(true);
                            CLEAN.setEnabled(true);
                      }
                  }
              }
          }
      }
      
      org.netbeans.api.project.ui.OpenProjects.getDefault().addPropertyChangeListener(
              new AJProjectsPropertyChangeListener()
      );//addPropertyChangeListener
   }   
   
   void doInstalled()
   {
      try
      {
            //get ref to actions
            CallableSystemAction BUILD = (CallableSystemAction) SystemAction.get(AJBuildAction.class);
            CallableSystemAction SELECT = (CallableSystemAction) SystemAction.get(AJSelectConfigurationAction.class);
            CallableSystemAction CLEAN = (CallableSystemAction) SystemAction.get(AJCleanAction.class);
            CallableSystemAction RUN = (CallableSystemAction) SystemAction.get(AJRunAction.class);
            CallableSystemAction DEBUG = (CallableSystemAction) SystemAction.get(AJDebugAction.class);
          
            logger.log(Level.INFO, "AJStartBrowserAction#performAction performing installed");
            System.out.println("AJStartBrowserAction#performAction performing installed");
            BUILD.setEnabled(false);
            SELECT.setEnabled(false);
            RUN.setEnabled(false);
            CLEAN.setEnabled(false);
            DEBUG.setEnabled(false);
            toggleStructureView(false);
            
            installed = false;
      }
      catch (Throwable throwable) {
         Ajde.getDefault().getErrorHandler().handleError("Could not stop AJDE.", throwable);
      }
   }
   
   void doNotInstalled()
   {
      try
      {  
         CallableSystemAction BUILD = (CallableSystemAction) SystemAction.get(AJBuildAction.class);
         CallableSystemAction SELECT = (CallableSystemAction) SystemAction.get(AJSelectConfigurationAction.class);
         CallableSystemAction CLEAN = (CallableSystemAction) SystemAction.get(AJCleanAction.class);
         
         logger.log(Level.INFO, "AJStartBrowserAction#performAction performing !installed");
         BUILD.setEnabled(true);
         SELECT.setEnabled(true);
         CLEAN.setEnabled(true);

         //mehrmals init
         NbManager.init();
         //mehrmals erzeugt?
         structureViewTab // = new StructureViewTab(NbManager.INSTANCE.getViewPanel())
           = new StructureViewComponent(NbManager.INSTANCE.getViewPanel());
         showStructureView();

         installed = true;
      }
      catch (Throwable throwable) {
         Ajde.getDefault().getErrorHandler().handleError("Could not start AJDE.", throwable);
      }
   }
   
   public void performAction() {
      logger.log(Level.INFO, "AJStartBrowserAction#performAction");
      
      if(org.netbeans.api.project.ui.OpenProjects.getDefault().getOpenProjects().length == 0)
      {
        javax.swing.JOptionPane.showMessageDialog(org.openide.windows.WindowManager.getDefault().getMainWindow(), 
                                                  "Please open or create a project before starting the AspectJ browser.");
        if(!installed)
          return;
      }
      else
      if(null == org.netbeans.api.project.ui.OpenProjects.getDefault().getMainProject())
      {
        javax.swing.JOptionPane.showMessageDialog(org.openide.windows.WindowManager.getDefault().getMainWindow(), 
                                                  "Please set the main project with the Run:Set Main Project menu before starting the AspectJ browser.");
        if(!installed)
          return;
      }
      else
      {
            Project project = OpenProjects.getDefault().getMainProject();
            if(null != project)
                doProperties(project);
      }
      
      if(!installed) 
      {
           doNotInstalled();
      } 
         
      else 
      {//installed
           doInstalled();
      }

      if(firstInstall) 
      {
           firstInstall = false;
      }
   }

   @Override
   public boolean getBooleanState() {
      return installed;
   }

   @Override
   public void setBooleanState(boolean bool) {
      performAction();
      if (EventQueue.isDispatchThread()){
         presenter.setSelected(installed);
      }
      else{
         EventQueue.invokeLater(new Runnable() {
            public void run() {
              presenter.setSelected(installed);
            }
         });
      }
   }

   @Override
   public Component getToolbarPresenter() {
      if (presenter == null) {
         presenter = new JToggleButton(this);
         presenter.setText(null);
         presenter.setToolTipText(this.getName());
      }

      return presenter;
   }

   public String getName() {
      return NbBundle.getMessage(org.aspectj.tools.ajde.netbeans.AJStartBrowserAction.class,
              "LBL_StartBrowserAction");
   }

   @Override
   protected String iconResource() {
      return "org/aspectj/ajde/resources/actions/startAjde.gif";
   }

   public HelpCtx getHelpCtx() {
      return HelpCtx.DEFAULT_HELP;
   }

   private void showStructureView() {
      Runnable runnable = new Runnable() {

         public void run() {
            AJStartBrowserAction.this.toggleStructureView(true);
            (structureViewTab).setVisible(true);
         }
      };

      if (SwingUtilities.isEventDispatchThread()) {
         runnable.run();
      } else {
         try {
            SwingUtilities.invokeAndWait(runnable);
         } catch (Exception exception) {
         }
      }
   }

   private void toggleStructureView(boolean flag) {
      try {

         // FileStructureViewWindowTopComponent fstc = FileStructureViewWindowTopComponent.findInstance();
         Mode mode = getExplorerMode();

         mode.dockInto(structureViewTab);

         if (flag) {
            structureViewTab.open();
            //only if name is "" ?
            structureViewTab.setName("AspectJ CrossRefs");
            structureViewTab.requestActive();
         } else {
            structureViewTab.close();
         }
      } catch (Exception exception) {
         Ajde.getDefault().getErrorHandler().handleError("Could not toggle structure view.", exception);
      }
   }

   private static Mode getExplorerMode() {
      return WindowManager.getDefault().findMode("navigator");
   }

   @Override
   protected void initialize() {
      super.initialize();
      super.setEnabled(true);
   }

   protected boolean asynchronous() {
      return false;
   }

   static class StructureViewComponent extends TopComponent {

      private final String PREFERRED_ID = "structureViewComponent";

      public StructureViewComponent(JPanel jpanel) {
         //this.setName("AspectJ Browser");//never seen, always reset
         this.setLayout(new BorderLayout());
         this.add(jpanel, "Center");
         this.add(jpanel);
         this.setBorder(null);
      }

      @Override
      public Image getIcon() {
         return ((ImageIcon) NbIconRegistry.INSTANCE.getStartAjdeIcon()).getImage();
      }

      // public SystemAction[] getSystemActions() {
//      return new SystemAction[0];
//        }
      @Override
      public int getPersistenceType() {
         return TopComponent.PERSISTENCE_NEVER;
      }

      @Override
      protected String preferredID() {
         return PREFERRED_ID;
      }
   }
}
//~ Formatted by Jindent --- http://www.jindent.com

