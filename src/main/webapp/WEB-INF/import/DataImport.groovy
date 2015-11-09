@Grapes([
    @Grab('mysql:mysql-connector-java:5.1.36')
])

import java.lang.reflect.Array;
import groovy.sql.Sql;

//Lembrar de dar um alter table no campo "description" de Photo e
//"content" de Article para "longtext"
//hash 8470d37d090fdb40a2c313d2d7c891f5

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
expando.server = this.args[0]
expando.inputDB = this.args[1]
expando.inputUser = this.args[2]
expando.inputPasswd = this.args[3]
expando.outputDB = this.args[4]
expando.outputUser = this.args[5]
expando.outputPasswd = this.args[6]

def importer = new Importer(expando)

def newAccountIds = ImportAccount.importAccount(importer),
	newSkinIds = ImportSkin.importSkin(importer),
	newPermanentLinkIds = ImportPermanentLink.importPermanentLink(importer),
	newPageIds = ImportPage.importPage(importer, newSkinIds, newPermanentLinkIds),
	newCategoryIds = ImportCategory.importCategory(importer, newSkinIds, newPermanentLinkIds),
	oldCategoryIds = ImportCategory.oldCategoryIds
ImportCategoryCategory.importCategoryCategory(importer, newCategoryIds)
def newPhotoIds = ImportPhoto.importPhoto(importer, newAccountIds, this.args[7]),
	newArticleIds = ImportArticle.importArticle(importer, oldCategoryIds, newPhotoIds, newCategoryIds, newSkinIds, newAccountIds, newPermanentLinkIds)
	newPhotoGalleryIds = ImportPhotoGallery.importPhotoGallery(importer, oldCategoryIds, newArticleIds),
	oldPhotoGalleryIds = ImportPhotoGallery.oldPhotoGalleryIds
ImportPhotoGalleryPhotos.importPhotoGalleryPhotos(importer, oldPhotoGalleryIds, newPhotoGalleryIds, newPhotoIds)
UpdatePermanentLink.updatePermanentLink(importer, newPermanentLinkIds, newArticleIds, newCategoryIds, newPageIds)
//TODO DefaultArticle
//TODO Videos

println new Date()