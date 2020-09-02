<html>

<head>
    <title>Employee Report</title>
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
<h3>${(reportTitle)!"Default Title"}</h3>
<br/>

<h4>Employee Details</h4>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Age</th>
        <th>Dob</th>
        <th>Salary</th>
    </tr>
    <#assign empCounter=1>
    <#list employees as empObj>
        <tr>
            <td>${empCounter}</td>
            <td>
                <a href="#">${empObj.name}</a>
            </td>
            <td>
                ${empObj.age}
            </td>
            <td>
                ${empObj.dob?date}
            </td>
            <td>
                <#if empObj.salary gt 50000>
                    <p class="green">
                        ${empObj.salary}
                    </p>
                <#else>
                    <p class="yellow">
                        ${empObj.salary}
                    </p>
                </#if>
            </td>
        </tr>
        <#assign empCounter=empCounter+1>
    </#list>
</table>
<h2>Total Employees: ${employees?size}</h2>
</body>
</html>
