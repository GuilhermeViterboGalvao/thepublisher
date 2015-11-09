class ImportPhotoGallery {
	static def oldPhotoGalleryIds = []
	static def importPhotoGallery(importer, oldCategoryIds, newArticleIds) {
		println "***********************************************"
		println "*Importando PHOTOGALLERY do SYSTEM para TATAME*"
		println "***********************************************"
		def newPhotoGalleryIds = [:]		
		def select = "select pg.* from PhotoGallery pg inner join Article a on pg.id = a.id where a.category_id in ("
		for (def i = 0; i < oldCategoryIds.size(); i++) {
		    if (i == (oldCategoryIds.size() - 1)) {
		        select += "?)"
		    } else {
		        select += "?, "    
		    }
		}
		importer.dbin.eachRow(select, oldCategoryIds) { row ->
		    if (row && row.id) {
		        oldPhotoGalleryIds.add(row.id)
		        def sql = "insert into PhotoGallery (id) values (?)"
		        def params = [
		            newArticleIds[row.id]
		        ]
		        importer.dbout.executeInsert(sql, params)
		        newPhotoGalleryIds.put(row.id, newArticleIds[row.id])
		        println "PhotoGallery old=${row.id} new=${newArticleIds[row.id]}"
		    }
		}
		return newPhotoGalleryIds	
	}
}