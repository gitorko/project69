<html>

    <head>
        <style>
            table {
                font-family: arial, sans-serif;
                border-collapse: collapse;
                width: 100%;
            }
            td,
            th {
                border: 1px solid #DDDDDD;
                text-align: left;
                padding: 8px;
            }
            th {
                background-color: #CCCCCC;
            }
            tr.script {
                background-color: #EEEEEE;
            }
            p.red {
                color: #C92100;
                font-weight: bold;
            }
            p.yellow {
                color: #FFDC0B;
                font-weight: bold;
            }
            p.green {
                color: #2F8400;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <h3>${reportTitle}</h3>
        <br/>

        <h4>Employee Details</h4>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>Dob</th>
            </tr>
            <#assign i=1>
            <#list employees as emp>
                <tr>
                    <td>${i}</td>

                </tr>
                <#assign i=i+1>
            </#list>
        </table>
    </body>
</html>
