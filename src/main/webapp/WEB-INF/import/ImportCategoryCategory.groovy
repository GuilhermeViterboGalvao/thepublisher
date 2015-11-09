class ImportCategoryCategory {
	static def importCategoryCategory(importer, newCategoryIds) {
		println "****************************************************"
		println "*Importando CATEGORY_CATEGORY do SYSTEM para TATAME*"
		println "****************************************************"
		importer.dbin.eachRow("select * from Category where name like '%tatame%'") { row ->
		    importer.dbin.eachRow("select * from Category_Category where Category_id = ?", [row.id]) { row2 ->
		        if (row2 && row2.Category_id && row2.parents_id) {
		            def sql = """
		                insert into Category_Category (Category_id, parents_id) 
		                values (?, ?)
		            """
		            def params = [
		                newCategoryIds[row2.Category_id],
		                newCategoryIds[row2.parents_id]
		            ]
		            importer.dbout.executeInsert(sql, params)
		            println "Category_Category old=[${row2.Category_id}, ${row2.parents_id}] new=[${newCategoryIds[row2.Category_id]}, ${newCategoryIds[row2.parents_id]}]"        
		        }
		    }
		}	
	}
}