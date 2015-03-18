/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@org.netbeans.api.templates.TemplateRegistrations
({
  @TemplateRegistration(
        folder = "AspectJ",
        iconBase="org/ajfilesupport/Datasource.gif", 
        displayName = "#AJFiletemplate_displayName", 
        content = "AJTemplate.aj",
        description = "Description.html",
        scriptEngine="freemarker"),
          
  @TemplateRegistration(
        folder = "AspectJ",
        iconBase="org/ajfilesupport/Datasource.gif", 
        displayName = "#AJEmptyFile_displayName", 
        content = "new.aj",
        description = "Description.html",
        scriptEngine="freemarker")        
})
        
@Messages
(value = {
    "AJFiletemplate_displayName=Aspect J template file",
    "AJEmptyFile_displayName=Empty Aspect J file"
})

package org.ajfilesupport;

import org.netbeans.api.templates.TemplateRegistration;
import org.openide.util.NbBundle.Messages;