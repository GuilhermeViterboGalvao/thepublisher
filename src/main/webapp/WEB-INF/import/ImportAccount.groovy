class ImportAccount {
	static def importAccount(importer) {
		println "******************************************"
		println "*Importando ACCOUNT do SYSTEM para TATAME*"
		println "******************************************"
		def newAccountIds = [:]
		def accountId = 1
		importer.dbin.eachRow("select * from Account where email like '%tatame%'") { row ->
		    def sql = """
		        insert into Account (id, name, email, hash, securityHole, active) 
		        values (?, ?, ?, ?, ?, ?)
		    """
		    def params = [
		        accountId,
		        row.name,
		        row.email,
		        row.hash,
		        row.securityHole,
		        row.active
		    ]
		    importer.dbout.executeInsert(sql, params)
		    newAccountIds.put(row.id, accountId)
		    println "Account old=${row.id} new=${accountId}"
		    accountId++
		}
		return newAccountIds
	}
}