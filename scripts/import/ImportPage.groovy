class ImportPage {
	static def importPage(importer, newSkinIds, newPermanentLinkIds) {
		println "***************************************"
		println "*Importando PAGE do SYSTEM para TATAME*"
		println "***************************************"
		def newPageIds = [:]
		def pageId = 1
		importer.dbin.eachRow("select * from Page where name like '%tatame%'") { row ->
		    def sql = """
		        insert into Page (id, name, skin_id, contentFile, permanentLink_id) 
		        values (?, ?, ?, ?, ?)
		    """
		    def contentFile = row.contentFile.replaceAll("/system", "")
		    def params = [
		        pageId,
		        row.name,
		        newSkinIds[row.skin_id],
		        contentFile,
		        newPermanentLinkIds[row.permanentLink_id]
		    ]
		    importer.dbout.executeInsert(sql, params)
		    newPageIds.put(row.id, pageId)
		    println "Page old=${row.id} new=${pageId}"
		    pageId++
		}
		return newPageIds
	}
}