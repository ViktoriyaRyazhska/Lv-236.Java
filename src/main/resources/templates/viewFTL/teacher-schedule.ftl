<#import "header.ftl" as h>
<@h.header>

</@h.header>


<select>
<#list model["teacherList"] as list>
    <option value="${list.id}">${list.lastName}</option>
</#list>
</select>


<@h.footer>

</@h.footer>
