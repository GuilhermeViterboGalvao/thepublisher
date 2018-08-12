class ImportCategory {
	static def oldCategoryIds = []
	static def importCategory(importer, newSkinIds, newPermanentLinkIds) {
		println "*******************************************"
		println "*Importando CATEGORY do SYSTEM para TATAME*"
		println "*******************************************"
		def newCategoryIds = [:]		
		def categoryId = 1
		importer.dbin.eachRow("select * from Category where name like '%tatame%'") { row ->
		    oldCategoryIds.add(row.id)
		    def sql = """
		        insert into Category (id, name, tags, folder, skin_id, permanentLink_id) 
		        values (?, ?, ?, ?, ?, ?)
		    """
		    def params = [
		        categoryId,
		        row.name,
		        row.tags,
		        row.folder,
		        newSkinIds[row.skin_id],
		        newPermanentLinkIds[row.permanentLink_id]
		    ]
		    importer.dbout.executeInsert(sql, params)
		    newCategoryIds.put(row.id, categoryId)
		    println "Category old=${row.id} new=${categoryId}"
		    categoryId++
		}
		return newCategoryIds
	}
}