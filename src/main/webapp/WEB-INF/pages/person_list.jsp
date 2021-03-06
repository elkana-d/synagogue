<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html
<html>
<head>

<title>Insert title here</title>
<link href="styles/kendo.common.min.css" rel="stylesheet">
<link href="styles/kendo.default.min.css" rel="stylesheet">
<!-- jquery and kendo javascript. jquery MUST be first. -->
<script src="js/jquery.min.js"></script>
<script src="js/kendo.all.min.js"></script>

<c:url value="/grid/editing-inline/create" var="createUrl" />
<c:url value="/grid/editing-inline/read" var="readUrl" />
<c:url value="/grid/editing-inline/update" var="updateUrl" />
<c:url value="/grid/editing-inline/destroy" var="destroyUrl" />
</head>
<body>

    <kendo:grid name="grid" pageable="true" sortable="true">
         <kendo:grid-filterable></kendo:grid-filterable>
    	<kendo:grid-editable mode="inline" confirmation="Are you sure you want to remove this item?"/>
        <kendo:grid-toolbar>
            <kendo:grid-toolbarItem name="create" />
        </kendo:grid-toolbar>
        <kendo:grid-columns>
            <kendo:grid-column title="&nbsp;" width="250px">
                <kendo:grid-column-command>
                    <kendo:grid-column-commandItem name="edit" />
                    <kendo:grid-column-commandItem name="destroy" />
                    <kendo:grid-column-commandItem name="viewDetails" text="View Details">
                    <kendo:grid-column-commandItem-click>
                        <script>
                        function showDetails(e) {
                            var detailsTemplate = kendo.template($("#template").html());

                            e.preventDefault();

                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                            var wnd = $("#details").data("kendoWindow");

                            wnd.content(detailsTemplate(dataItem));
                            wnd.center().open();
                        }
                        </script>
                    </kendo:grid-column-commandItem-click>
               </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
            <kendo:grid-column title="הערות" field="note" width="120px"/>
            <kendo:grid-column title="מחיר" field="price" width="120px"
            template='#:priceTemplate(price)#'

            />
            <kendo:grid-column title="שם משפחה" field="lastName" width="120px" />
            <kendo:grid-column title="שם פרטי" field="firstName" width="120px"/>



        </kendo:grid-columns>
        <kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
            <kendo:dataSource-transport>
                <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
                <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-parameterMap>
                	<script>
	                	function parameterMap(options,type) {
	                		return JSON.stringify(options);
	                	}
                	</script>
                </kendo:dataSource-transport-parameterMap>
            </kendo:dataSource-transport>
            <kendo:dataSource-schema>
                <kendo:dataSource-schema-model id="id">
                    <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="firstName" type="string" >
                        	<kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="lastName" type="string">
                        	<kendo:dataSource-schema-model-field-validation/>
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="price" type="number">
                            <kendo:dataSource-schema-model-field-validation required="true"/>
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="note" type="string">
                        	<kendo:dataSource-schema-model-field-validation/>
                        </kendo:dataSource-schema-model-field>
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>


    <script>

    function priceTemplate(value)
    {
        if (value>=0)
            return value;
        else
            return  -value + "-";
    }


    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules
                 productnamevalidation: function (input, params) {
                     //check for the name attribute
                     debugger;
                     if (input.filter("[name='price']").length && input.val()) {
                      return isNormalInteger(input.val());
                                                 }
                     return true;
                 }
             },
             messages: { //custom rules messages
                 productnamevalidation: "Product Name should start with capital letter"
             }
        });
    })(jQuery, kendo);

    //if the string is isInteger can be "55.5"
    function isNormalInteger(str) {
        var n = Math.floor(Number(str));
        return String(n) === str;
    }



    function onRequestEnd(e) {
        if(e.type == "read"){
        }
        else if (e.type == "create" && e.response.id != null) {
           alert("השמירה הצליחה בהצלחה");

        }
        else if (e.type == "update" && e.response.id) {
            alert("עדכון הצליח");
        }
        else if (e.type == "destroy") {

        }
        else {
            alert("השם והשם משפחה כבר קיימים במערכת");
        }

    }

     </script>








     <script type="text/x-kendo-template" id="template">
         <div id="details-container">
             <h2>#= firstName # #= lastName #</h2>
             <dl>

             </dl>
         </div>
         </script>

         <kendo:window name="details" modal="true" draggable="true" visible="false" />
     <style type="text/css">
          #details-container
          {
              padding: 10px;
          }

          #details-container h2
          {
              margin: 0;
          }

          #details-container em
          {
              color: #8c8c8c;
          }

          #details-container dt
          {
              margin:0;
              display: inline;
          }
      </style>




    </body>
    </html>