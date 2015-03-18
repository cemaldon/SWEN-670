/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


@org.netbeans.api.templates.TemplateRegistrations(
  {
    @TemplateRegistration(
        folder = "AspectJ",
        iconBase="org/aspectjlstfilesupport/ajcu_obj.gif", 
        displayName = "#LSTSourceRoots_displayName", 
        content = "LstTemplate.lst",
        description = "Desc_Source-Roots.html",
        scriptEngine="freemarker"),
    

    @TemplateRegistration(
        folder = "AspectJ",
        iconBase="org/aspectjlstfilesupport/ajcu_obj.gif", 
        displayName = "#EmptyLSTFile_displayName", 
        content = "empty.lst",
        description = "Desc_Empty_LST.html",
        scriptEngine="freemarker")
    
  }
)

@Messages(value = {
    "LSTSourceRoots_displayName=Template LST build configuration file",
    "EmptyLSTFile_displayName=Empty LST file"
})

package org.aspectjlstfilesupport;

import org.netbeans.api.templates.TemplateRegistration;
import org.openide.util.NbBundle.Messages;

//@TemplateRegistration(folder = "Other", content = "AJTemplate.aj")


