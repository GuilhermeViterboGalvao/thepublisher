class ImportPermanentLink {
	static def importPermanentLink(importer) {
		println "************************************************"
		println "*Importando PERMANENTLINK do SYSTEM para TATAME*"
		println "************************************************"
		def newPermanentLinkIds = [:]
		def permanentLinkId = 1
		importer.dbin.eachRow("select * from PermanentLink where url like '%tatame%'") { row ->
		    def sql = """
		        insert into PermanentLink (id, uri, type, param, created, moved) 
		        values (?, ?, ?, ?, ?, ?)
		    """
		    def params = [
		        permanentLinkId,
		        row.url,
		        row.type,
		        row.param,
		        row.created,
		        row.moved
		    ]
		    importer.dbout.executeInsert(sql, params)
		    newPermanentLinkIds.put(row.id, permanentLinkId)
		    println "PermanentLink old=${row.id} new=${permanentLinkId}"
		    permanentLinkId++
		}
		return newPermanentLinkIds
	}
}