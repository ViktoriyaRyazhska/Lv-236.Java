<#import "header.ftl" as h>
<@h.header>

</@h.header>
<script>document.title = 'Pageable';</script>
<#list schedules as schedule>
    <p>${schedule.homework}   ${schedule.teacherFirstName}  ${schedule.teacherLastName}</p>
</#list>

<div>
    <form action="page" method="get">
        <select name="size">
            <#list [5, 10, 15, 20] as s>
                <#if sizes == s>
                    <option value="${s}" selected="selected">${s} items</option>
                <#else>
                    <option value="${s}">${s} items</option>
                </#if>
            </#list>
        </select>
        <button type="submit">use</button>
    </form>
    <nav aria-label="...">
        <ul class="pagination">
            <#list 0..longs-1 as i>
                <#if current != i>
                    <li class="page-item"><a class="page-link" href="?page=${i}&size=${sizes}">${i+1}</a></li>
                <#else>
                    <li class="page-item active"><span class="page-link">${i+1}</span></li>
                </#if>
            </#list>
        </ul>
    </nav>
</div>
<@h.footer>

</@h.footer>
