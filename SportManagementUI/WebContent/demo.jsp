<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link href="styles/kendo.common.min.css" rel="stylesheet" />
    <link href="styles/kendo.default.min.css" rel="stylesheet" />
    <link href="styles/kendo.dataviz.min.css" rel="stylesheet" />
    <link href="styles/kendo.dataviz.default.min.css" rel="stylesheet" />
    <script src="js/jquery.min.js"></script>
    <script src="js/kendo.all.min.js"></script>
</head>
<body>
    <div id="example">

    <div class="demo-section k-header">
        <a class="k-button k-button-icontext k-add-button" href="#"><span class="k-icon k-add"></span>Add new record</a>
    </div>

    <div class="demo-section k-header">
        <div id="listView"></div>
        <div id="pager" class="k-pager-wrap"></div>
    </div>

    <script type="text/x-kendo-tmpl" id="template">
        <div class="product-view k-widget">
            <div class="edit-buttons">
                <a class="k-button k-button-icontext k-edit-button" href="\\#"><span class="k-icon k-edit"></span></a>
                <a class="k-button k-button-icontext k-delete-button" href="\\#"><span class="k-icon k-delete"></span></a>
            </div>
            <dl>
                <dt>Product Name</dt>
                <dd>#:ProductName#</dd>
                <dt>Unit Price</dt>
                <dd>#:kendo.toString(UnitPrice, "c")#</dd>
                <dt>Units In Stock</dt>
                <dd>#:UnitsInStock#</dd>
                <dt>Discontinued</dt>
                <dd>#:Discontinued#</dd>
            </dl>
        </div>
    </script>

    <script type="text/x-kendo-tmpl" id="editTemplate">
        <div class="product-view k-widget">
            <div class="edit-buttons">
                <a class="k-button k-button-icontext k-update-button" href="\\#"><span class="k-icon k-update"></span></a>
                <a class="k-button k-button-icontext k-cancel-button" href="\\#"><span class="k-icon k-cancel"></span></a>
            </div>
            <dl>
                <dt>Product Name</dt>
                <dd>
                    <input type="text" class="k-textbox" data-bind="value:ProductName" name="ProductName" required="required" validationMessage="required" />
                    <span data-for="ProductName" class="k-invalid-msg"></span>
                </dd>
                <dt>Unit Price</dt>
                <dd>
                    <input type="text" data-bind="value:UnitPrice" data-role="numerictextbox" data-type="number" name="UnitPrice" required="required" min="1" validationMessage="required" />
                    <span data-for="UnitPrice" class="k-invalid-msg"></span>
                </dd>
                <dt>Units In Stock</dt>
                <dd>
                    <input type="text" data-bind="value:UnitsInStock" data-role="numerictextbox" name="UnitsInStock" required="required" data-type="number" min="0" validationMessage="required" />
                    <span data-for="UnitsInStock" class="k-invalid-msg"></span>
                </dd>
                <dt>Discontinued</dt>
                <dd><input type="checkbox" name="Discontinued" data-bind="checked:Discontinued"></dd>
            </dl>
        </div>
    </script>

    <script>
        $(document).ready(function () {
            var crudServiceBaseUrl = "http://demos.telerik.com/kendo-ui/service",
                dataSource = new kendo.data.DataSource({
                    transport: {
                        read:  {
                            url: crudServiceBaseUrl + "/Products",
                            dataType: "jsonp"
                        },
                        update: {
                            url: crudServiceBaseUrl + "/Products/Update",
                            dataType: "jsonp"
                        },
                        destroy: {
                            url: crudServiceBaseUrl + "/Products/Destroy",
                            dataType: "jsonp"
                        },
                        create: {
                            url: crudServiceBaseUrl + "/Products/Create",
                            dataType: "jsonp"
                        },
                        parameterMap: function(options, operation) {
                            if (operation !== "read" && options.models) {
                                return {models: kendo.stringify(options.models)};
                            }
                        }
                    },
                    batch: true,
                    pageSize: 4,
                    schema: {
                        model: {
                            id: "ProductID",
                            fields: {
                                ProductID: { editable: false, nullable: true },
                                ProductName: "ProductName",
                                UnitPrice: { type: "number" },
                                Discontinued: { type: "boolean" },
                                UnitsInStock: { type: "number" }
                            }
                        }
                    }
                });


            $("#pager").kendoPager({
                dataSource: dataSource
            });

            var listView = $("#listView").kendoListView({
                dataSource: dataSource,
                template: kendo.template($("#template").html()),
                editTemplate: kendo.template($("#editTemplate").html())
            }).data("kendoListView");

            $(".k-add-button").click(function(e) {
                listView.add();
                e.preventDefault();
            });
        });
    </script>

    <style scoped>
        .demo-section {
            width: 700px;
        }
        .product-view
        {
            float: left;
            position: relative;
            width: 349px;
            margin: 0 -1px -1px 0;
        }
        .product-view dl
        {
            margin: 10px 0;
            padding: 0;
            min-width: 0;
            overflow: hidden;
        }
        .product-view dt, dd
        {
            float: left;
            margin: 0;
            padding: 3px;
            height: 32px;
            width: 180px;
            line-height: 32px;
            overflow: hidden;
        }
        .product-view dt
        {
            clear: left;
            padding: 3px 5px 3px 0;
            text-align: right;
            opacity: 0.6;
            width: 100px;
        }
        .k-listview
        {
            border: 0;
            padding: 0;
            overflow: hidden;
            min-height: 298px;
        }
        .edit-buttons
        {
            position: absolute;
            top: 0;
            right: 0;
            width: 26px;
            height: 170px;
            padding: 2px 2px 0 3px;
            background-color: rgba(0,0,0,0.1);
        }
        .edit-buttons .k-button
        {
            width: 26px;
            margin-bottom: 1px;
        }
        .k-pager-wrap
        {
            border-top: 0;
        }
        span.k-invalid-msg
        {
            position: absolute;
            margin-left: 6px;
        }
    </style>
</div>


</body>
</html>