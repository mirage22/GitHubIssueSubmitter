<%@ page import="grails.tado.training.Issue" %>



<div class="fieldcontain ${hasErrors(bean: issueInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="issue.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${issueInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: issueInstance, field: 'content', 'error')} required">
	<label for="content">
		<g:message code="issue.content.label" default="Content" />
		<span class="required-indicator">*</span>
	</label>
    <g:textArea name="content" value="${issueInstance?.content}" required="" rows="10" cols="25"/>
</div>

