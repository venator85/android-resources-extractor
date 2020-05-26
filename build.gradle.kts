plugins {
	id("org.jetbrains.kotlin.jvm") version "1.3.72"
	application
}

repositories {
	jcenter()
}

dependencies {
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("xmlpull:xmlpull:1.1.3.1")
	implementation("net.sf.kxml:kxml2:2.3.0")
	implementation("xom:xom:1.3.5")
}

application {
	mainClassName = "eu.alessiobianchi.resourcesextractor.AppKt"
}
