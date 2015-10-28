@Grapes([
    @Grab('mysql:mysql-connector-java:5.1.36')
])

import java.lang.reflect.Array;
import groovy.sql.Sql;

//Lembrar de dar um alter table no campo "description" de Photo e
//"content" de Article para "longtext"

println new Date()

class Importer {
        
    def dbin
    
    def dbout
    
    Importer(params) {
        def source1 = new com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource()
        source1.url = "jdbc:mysql://${params.server}:3307/${params.inputDB}?useUnicode=true"
        source1.user = params.inputUser
        source1.password = params.inputPasswd
        source1.zeroDateTimeBehavior = "convertToNull"
        dbin = new Sql(source1)//SYSTEM
        
        def source2 = new com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource()
        source2.url = "jdbc:mysql://${params.server}:3307/${params.outputDB}?useUnicode=true"
        source2.user = params.outputUser
        source2.password = params.outputPasswd
        source2.zeroDateTimeBehavior = "convertToNull"
        dbout = new Sql(source2)//TATAME
    }    
}

def expando = new Expando()
expando.server = "localhost"//this.args[0]
expando.inputDB = "system"//this.args[1]
expando.inputUser = "system"//this.args[2]
expando.inputPasswd = "system"//this.args[3]
expando.outputDB = "thepublisher"//this.args[4]
expando.outputUser = "thepublisher"//this.args[5]
expando.outputPasswd = "thepublisher"//this.args[6]
//params.inputPhotoPath = this.args[7]
//params.outputPhotoPath = this.args[8]

def importer = new Importer(expando)

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
    def params = [
        skinId,
        row.name,
        row.path,
        row.customContentFolder
    ]
    importer.dbout.executeInsert(sql, params)
    newSkinIds.put(row.id, skinId)
    println "Skin old=${row.id} new=${skinId}"    
    skinId++
}

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
    def params = [
        pageId,
        row.name,
        newSkinIds[row.skin_id],
        row.contentFile,
        newPermanentLinkIds[row.permanentLink_id]
    ]
    importer.dbout.executeInsert(sql, params)
    newPageIds.put(row.id, pageId)
    println "Page old=${row.id} new=${pageId}"
    pageId++
}

println "*******************************************"
println "*Importando CATEGORY do SYSTEM para TATAME*"
println "*******************************************"
def newCategoryIds = [:]
def oldCategoryIds = []
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

println "****************************************************"
println "*Importando CATEGORY_CATEGORY do SYSTEM para TATAME*"
println "****************************************************"
importer.dbin.eachRow("select * from Category where name like '%tatame%'") { row ->
    importer.dbin.eachRow("select * from Category_Category where Category_id = ?", [row.id]) { row2 ->
        if (row2 && row2.Category_id && row2.parents_id) {
            def sql = """
                insert into Category_Category (Category_id, parents_id) 
                values (?, ?)
            """
            def params = [
                newCategoryIds[row2.Category_id],
                newCategoryIds[row2.parents_id]
            ]
            importer.dbout.executeInsert(sql, params)
            println "Category_Category old=[${row2.Category_id}, ${row2.parents_id}] new=[${newCategoryIds[row2.Category_id]}, ${newCategoryIds[row2.parents_id]}]"        
        }
    }
}

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
    newPhotoIds.put(row.id, photoId)    
    def outputFolder = new File("/Users/Guilherme/tatame-import", "${photoId - photoId%1000}")
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
    def params = [
        articleId,
        row.header,
        row.title,
        row.tags,
        row.note,
        row.content,
        row.photo_id > 0 ? newPhotoIds[row.photo_id] : 0,
        row.category_id > 0 ? newCategoryIds[row.category_id] : 0,
        row.template_id > 0 ? newSkinIds[row.template_id] : 0,
        row.publishedAt,
        row.createdBy_id > 0 ? newAccountIds[row.createdBy_id] : 0,
        row.created,
        row.lastModifiedBy_id > 0 ? newAccountIds[row.lastModifiedBy_id] : 0,
        row.lastModified,
        row.published,
        row.forumEnabled,
        row.permanentLink_id > 0 ? newPermanentLinkIds[row.permanentLink_id] : 0,
        row.views
    ]      
    importer.dbout.executeInsert(sql, params)
    newArticleIds.put(row.id, articleId)
    println "Article old=${row.id} new=${articleId}"
    articleId++
}

println "***********************************************"
println "*Importando PHOTOGALLERY do SYSTEM para TATAME*"
println "***********************************************"
def newPhotoGalleryIds = [:]
def oldPhotoGalleryIds = []
select = "select pg.* from PhotoGallery pg inner join Article a on pg.id = a.id where a.category_id in ("
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
        def newId = importer.dbout.executeInsert(sql, params)[0][0]    
        newPhotoGalleryIds.put(row.id, newId)
        println "PhotoGallery old=${row.id} new=${newId}"
    }
}

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

//TODO DefaultArticle
//TODO Videos

println new Date()