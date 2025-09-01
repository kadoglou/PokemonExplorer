import com.android.build.gradle.LibraryExtension
import helper.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension
import kotlin.collections.forEach
import kotlin.jvm.java

abstract class ConventionPlugin : Plugin<Project> {

    protected lateinit var libs: VersionCatalog

    override fun apply(target: Project): Unit = with(target) {
        libs = getLibs()
        applyPlugins(target, plugins())
        configureKotlin(target)
        configureAndroid(target)
        configureProject(target)
    }

    // Subclasses override this to declare what plugins they want
    protected open fun plugins(): List<String> = emptyList()

    protected open fun KotlinAndroidExtension.kotlinBlock(target: Project) {}

    // Other settings can be adjusted here
    protected open fun LibraryExtension.androidBlock(target: Project): Unit = with(target) {}

    // Other settings can be adjusted here
    protected open fun configureProject(target: Project): Unit = with(target) {}

    private fun applyPlugins(target: Project, pluginAliases: List<String>) {
        pluginAliases.forEach { alias ->
            val plugin = libs.findPlugin(alias).get().get()
            target.plugins.apply(plugin.pluginId)
        }
    }

    private fun configureKotlin(target: Project) {
        target.extensions.findByType(KotlinAndroidExtension::class.java)?.kotlinBlock(target)
    }

    private fun configureAndroid(target: Project) {
        target.extensions.findByType(LibraryExtension::class.java)?.androidBlock(target)
    }
}