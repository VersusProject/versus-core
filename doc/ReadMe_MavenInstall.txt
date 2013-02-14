Instructions for setting up Maven for Versus in Eclipse.

1. Navigate to your local Maven folder named .m2
		Create a file called settings.xml in the .m2 folder
	
2. Paste the following xml into the new settings.xml file
		NOTE: These settings are located at http://vm3-050.nist.gov/hudson -> Manage Hudson -> Maven 3 Configuration -> ID File

<settings>
	<mirrors>
		<mirror>
			<!--This sends everything else to /public -->
			<id>nexus</id>
			<mirrorOf>*</mirrorOf>
			<url>http://vm-070.nist.gov:8081/nexus/content/groups/public</url>
		</mirror>
	</mirrors>
	<profiles>
		<profile>
			<id>nexus</id>
			<!--Enable snapshots for the built in central repo to direct -->
			<!--all requests to nexus via the mirror -->
			<repositories>
				<repository>
					<id>central</id>
					<url>http://central</url>
					<releases>
						<enabled>true</enabled>
					</releases>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</repository>
			</repositories>
			<pluginRepositories>
				<pluginRepository>
					<id>central</id>
					<url>http://central</url>
					<releases>
						<enabled>true</enabled>
					</releases>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</pluginRepository>
			</pluginRepositories>
		</profile>
	</profiles>
	<activeProfiles>
		<!--make the profile active all the time -->
		<activeProfile>nexus</activeProfile>
	</activeProfiles>
	<servers>
		<server>
			<id>snapshots</id>
			<username>deployment</username>
			<password>deployment123</password>
		</server>
		<server>
			<id>releases</id>
			<username>deployment</username>
			<password>deployment123</password>
		</server>
		<server>
			<id>thirdparty</id>
			<username>deployment</username>
			<password>deployment123</password>
		</server>
	</servers>
</settings>

3. In Eclipse, right click on project and goto Run As -> Maven Install
		NOTE: Make sure Eclipse is set up to run Java as a JDK, NOT a JRE
			Goto Window -> Preferences -> Java -> Installed JREs, there should be a JDK option available, make sure it is checked
		Eclipse will begin downloading external jars and may take a while
		
4. Finished
