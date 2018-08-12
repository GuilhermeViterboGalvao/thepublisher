class ImportArticle {
	static def importArticle(importer, oldCategoryIds, newPhotoIds, newCategoryIds, newSkinIds, newAccountIds, newPermanentLinkIds) {
		println "******************************************"
		println "*Importando ARTICLE do SYSTEM para TATAME*"
		println "******************************************"
		def newArticleIds = [:]
		def articleId = 1
		def select = "select * from Article where category_id in ("
		for (def i = 0; i < oldCategoryIds.size(); i++) {
		    if (i == (oldCategoryIds.size() - 1)) {
		        select += "?)"
		    } else {
		        select += "?, "    
		    }
		}
		importer.dbin.eachRow(select, oldCategoryIds) { row ->
		    def sql = """
		        insert into Article (id, header, title, tags, note, content, photo_id, category_id, template_id, publishedAt, createdBy_id, created, lastModifiedBy_id, lastModified, published, forumEnabled, permanentLink_id, views) 
		        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		    """
		    def content = row.content
		    def matches = (content =~ /\/img\/\d{1,999999}/)
		    if (matches && matches.size() > 0) {
		        for (def match : matches) {
		            def oldPhotoId = 0
		            try {
		               oldPhotoId = Long.parseLong(match.replaceAll("/img/", ""))
		            } catch(Exception e) {}
		            if (oldPhotoId > 0) {
		                content = content.replaceAll(match, "/img/${newPhotoIds[oldPhotoId]}")            
		            }
		        }
		    }
		    def params = [
		        articleId,
		        row.header,
		        row.title,
		        row.tags,
		        row.note,
		        content,
		        row.photo_id && row.photo_id > 0 ? newPhotoIds[row.photo_id] : null,
		        row.category_id && row.category_id > 0 ? newCategoryIds[row.category_id] : null,
		        row.template_id && row.template_id > 0 ? newSkinIds[row.template_id] : null,
		        row.publishedAt,
		        row.createdBy_id && row.createdBy_id > 0 ? newAccountIds[row.createdBy_id] : null,
		        row.created,
		        row.lastModifiedBy_id && row.lastModifiedBy_id > 0 ? newAccountIds[row.lastModifiedBy_id] : null,
		        row.lastModified,
		        row.published,
		        row.forumEnabled,
		        row.permanentLink_id && row.permanentLink_id > 0 ? newPermanentLinkIds[row.permanentLink_id] : null,
		        row.views
		    ]      
		    importer.dbout.executeInsert(sql, params)
		    newArticleIds.put(row.id, articleId)
		    println "Article old=${row.id} new=${articleId}"
		    articleId++
		}
		return newArticleIds
	}
}