<#assign test = "{\"foo\":\"bar\", \"f\":4, \"text\":\"bla bla\"}">
<#assign m = test?eval>

${m.foo}  <#-- prints: bar -->

<#-- Dump the whole map: -->
<#list m?keys as k>
  ${k} => ${m[k]}
</#list>