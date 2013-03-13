<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'mainTado.label', default: 'MainTado')}"/>
    <r:require modules="bootstrap"/>
</head>
<g:javascript library="jquery" plugin="jquery"/>
<g:javascript>
    $(document).ready(function(){

    });

    function issue_send(){

        var title = $("#title").val();
        var body = $("#body").val();

        console.log("send SOMEHTING title" + title + " BODY" + body);
        $('#myModal').modal('hide')

    }

</g:javascript>
<body>

    <h1>Twitter Bootstrap Plugin</h1>
    <g:link controller="issue" action="index"></g:link>

    <a href="#myModal" role="button" type="button" class="btn btn-small btn-primary" data-toggle="modal">Launch demo modal</a>

    <!-- Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">Report Issue</h3>
        </div>
        <div class="modal-body">
            <label>Title</label>
            <input id="title" type="text" placeholder="Type something…">
            <label>Body</label>
             <textarea id="body" rows="3"></textarea>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button onclick="issue_send()" class="btn btn-primary">Send Issue</button>
        </div>
    </div>

</body>

</html>