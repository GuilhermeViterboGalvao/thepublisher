class ImportPhoto {
	static def importPhoto(importer, newAccountIds, path) {
		println "****************************************"
		println "*Importando PHOTO do SYSTEM para TATAME*"
		println "****************************************"
		def newPhotoIds = [:]
		def photoId = 1
		importer.dbin.eachRow("select * from Photo where isTatame = ?", [ true ]) { row ->
		    def sql = """
		        insert into Photo (id, tags, description, horizontalCenter, verticalCenter, width, height, credits, date, createdBy_id, created, lastModifiedBy_id, lastModified, published) 
		        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		    """
		    def credits = ""
		    importer.dbin.eachRow("select * from Collaborator where id = ?", [ row.photographer_id ]) { row2 ->
		        def name = ""
		        if (row2 && row2.name) {
		            name = new String(row2.name.toString().getBytes(), "UTF-8")
		        }
		        def email = ""
		        if (row2 && row2.email) {
		            email = new String(row2.email.toString().getBytes(), "UTF-8")
		        }
		        if (name && !name.isEmpty() && email && !email.isEmpty()) {
		            credits = "${name} - ${email}"
		        }
		    }
		    def params = [
		        photoId,
		        row.tags,
		        row.description,
		        row.horizontalCenter,
		        row.verticalCenter,
		        row.width,
		        row.height,
		        new String(credits.toString().getBytes(), "UTF-8"),
		        row.date,
		        newAccountIds[row.createdBy_id],
		        row.created,
		        newAccountIds[row.lastModifiedBy_id],
		        row.lastModified,
		        row.published
		    ]
		    importer.dbout.executeInsert(sql, params)
		    newPhotoIds.put(Long.parseLong("${row.id}"), photoId)    
		    def outputFolder = new File(path, "${photoId - photoId%1000}")
		    if (!outputFolder.exists()) {
		        outputFolder.mkdirs()
		    }
		    println outputFolder    
		    def image,
		        outputFile = new File(outputFolder, "${photoId}.jpg")
		    println outputFile
		    if (!outputFile.exists()) {
		        def outputStream
		        try {
		            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile))
		            outputStream << new URL("http://www.tatame.com.br/img/${row.id}.jpg").openStream()
		            image = ImageIO.read(outputFile)
		        } catch (Exception e) {
		            image = null
		        } finally {
		            if (outputStream != null) {
		                try {
		                    outputStream.close()
		                } catch (Exception e) { }
		            }
		        }    
		    }
		    println "Photo old=${row.id} new=${photoId}"
		    photoId++ 
		}
		return newPhotoIds
	}
}