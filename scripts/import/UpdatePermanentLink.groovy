class UpdatePermanentLink {
	static def updatePermanentLink(importer, newPermanentLinkIds, newArticleIds, newCategoryIds, newPageIds) {
		println "*****************************************"
		println "*Atualizando parâmetros do PERMANENTLINK*"
		println "*****************************************"
		importer.dbin.eachRow("select * from PermanentLink where url like '%tatame%'") { row ->
		    def newId = newPermanentLinkIds[row.id]
		    importer.dbout.eachRow("select * from PermanentLink where id = ?", [ newId ]) { row2 ->
		        def type = row2.type
		        if(type && type.equals("redirect")) {
		            importer.dbout.execute("update PermanentLink set param = ? where id = ?", [ newPermanentLinkIds[row2.param], row2.id ])
		            println "TYPE = ${type} - id=${row2.id} oldParam=${row2.param} newParam=${newPermanentLinkIds[row2.param]}"
		        } else if (type && type.equals("article")) {
		            importer.dbout.execute("update PermanentLink set param = ? where id = ?", [ newArticleIds[row2.param], row2.id ])
		            println "TYPE = ${type} - id=${row2.id} oldParam=${row2.param} newParam=${newArticleIds[row2.param]}"        
		        } else if (type && type.equals("category")) {
		            importer.dbout.execute("update PermanentLink set param = ? where id = ?", [ newCategoryIds[row2.param], row2.id ])
		            println "TYPE = ${type} - id=${row2.id} oldParam=${row2.param} newParam=${newCategoryIds[row2.param]}"
		        } else if (type && type.equals("page")) {
		            importer.dbout.execute("update PermanentLink set param = ? where id = ?", [ newPageIds[row2.param], row2.id ])
		            println "TYPE = ${type} - id=${row2.id} oldParam=${row2.param} newParam=${newPageIds[row2.param]}"
		        }
		    }
		}	
	} 
}