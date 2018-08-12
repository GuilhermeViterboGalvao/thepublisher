class ImportSkin {
	static def importSkin(importer) {
		println "***************************************"
		println "*Importando SKIN do SYSTEM para TATAME*"
		println "***************************************"
		def newSkinIds = [:]
		def skinId = 1
		importer.dbin.eachRow("select * from Skin where customContentFolder like '%tatame%'") { row ->
		    def sql = """
		        insert into Skin (id, name, path, contentFolder) 
		        values (?, ?, ?, ?)
		    """
		    def path = row.path.replaceAll("/system", "")
		    def customContentFolder = row.customContentFolder.replaceAll("/system", "")
		    def params = [
		        skinId,
		        row.name,
		        path,
		        customContentFolder
		    ]
		    importer.dbout.executeInsert(sql, params)
		    newSkinIds.put(row.id, skinId)
		    println "Skin old=${row.id} new=${skinId}"    
		    skinId++
		}
		return newSkinIds
	}
}