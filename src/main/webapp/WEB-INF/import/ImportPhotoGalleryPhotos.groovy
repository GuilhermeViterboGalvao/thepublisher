class ImportPhotoGalleryPhotos {
	static def importPhotoGalleryPhotos(importer, oldPhotoGalleryIds, newPhotoGalleryIds, newPhotoIds) {
		println "******************************************************"
		println "*Importando PHOTOGALLERY_PHOTOS do SYSTEM para TATAME*"
		println "******************************************************"
		select = "select * from PhotoGallery_photos where PhotoGallery_id in ("
		for (def i = 0; i < oldPhotoGalleryIds.size(); i++) {
		    if (i == (oldPhotoGalleryIds.size() - 1)) {
		        select += "?)"
		    } else {
		        select += "?, "    
		    }
		}
		importer.dbin.eachRow(select, oldPhotoGalleryIds) { row ->
		    def sql = """
		        insert into PhotoGallery_photos (PhotoGallery_id, description, photo_id, position)
		        values (?, ?, ?, ?)
		    """
		    def params = [
		        newPhotoGalleryIds[row.PhotoGallery_id],
		        row.description,
		        newPhotoIds[row.photo_id],
		        row.position
		    ]
		    importer.dbout.executeInsert(sql, params)
		    println "PhotoGallery_id old=${row.PhotoGallery_id} new=${newPhotoGalleryIds[row.PhotoGallery_id]}"
		}	
	}
}