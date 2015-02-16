/**
 * BP, anthill strategy game
 * Database of worlds for the game
 *
 * @author  xsimet00 Vojtech Simetka
 * @date    2013/05/10
 * @version 1
 * @file    graphic.WorldsDatavase.java
 */

package Enviroment;

import graphic.EnumTeams;
import java.awt.Point;

/**
 * Database of worlds for the game
 * @author Vojtech Simetka
 *
 */
public class WorldsDatabase
{
	/**
	 * Implemented worlds enumerator
	 */
	enum Worlds
	{
		Custom1,
		Custom2,
		Custom3,
		ResourceExperiment1,
		ResourceExperiment2,
		ResourceExperiment3,
		DeathMatch,
		Random,
	}

	/**
	 * GameMaps factory
	 * @param world Reference to the world
	 */
	public static void createWorld(GameMap world)
	{
		switch(Model.getConfiguration().getScenario())
		{
		case Custom1:
			WorldsDatabase.world1(world);
			break;
		case Custom2:
			WorldsDatabase.world2(world);
			break;
		case Custom3:
			WorldsDatabase.world3(world);
			break;
		case ResourceExperiment1:
			WorldsDatabase.worldResourceExperiment1(world);
			break;
		case ResourceExperiment2:
			WorldsDatabase.worldResourceExperiment2(world);
			break;
		case ResourceExperiment3:
			WorldsDatabase.worldResourceExperiment3(world);
			break;
		case DeathMatch:
			WorldsDatabase.worldDeathMatch(world);
			break;
		case Random:
			WorldsDatabase.randomWorld(world);
			break;
		default:
			System.out.println("Scenario with number" + Model.getConfiguration().getScenario() + " wasn't implemented");
			WorldsDatabase.world1(world);	
		}
	}
	/**
	 * Creates world 1
	 * @param world Reference to the world
	 */
	private static void world1(GameMap world)
	{	
		world.setWidth(1000);
		world.setHeight(600);
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 20, 20);
		world.addAnthill(EnumTeams.b, 980, 580);
		world.addAnthill(EnumTeams.c, 980, 20);
		world.addAnthill(EnumTeams.d, 20, 580);
		
		// Obstacles
		world.addObstacle(new Point(800,100), new Point(500,200), new Point(600,300));
		world.addObstacle(new Point(780,200), new Point(780,400), new Point(950,300));
		world.addObstacle(new Point(600,580), new Point(300,580), new Point(100,510));
		world.addObstacle(new Point(150,300), new Point(200, 20), new Point(600, 20));
		world.addObstacle(new Point(  1,500), new Point(200,500), new Point( 50,300));
		world.addObstacle(new Point(500,500), new Point(400,400), new Point(500,300));
		world.addObstacle(new Point(250,350), new Point(350,400), new Point(500,250));
		world.addObstacle(new Point(800,200), new Point(800,300), new Point(700,250));

		// Water
		world.addWater(50, 300, 3);
		world.addWater(150, 50, 3);
		world.addWater(250, 450, 3);
		world.addWater(350, 340, 3);
		world.addWater(550, 150, 3);
		
		// Food
		world.addFood(40, 40, 5);
		world.addFood(100, 400, 5);
		world.addFood(300, 400, 5);
		world.addFood(200, 400, 5);
		world.addFood(100, 200, 5);
		
		world.addMap("/img/world.png");
	}
	
	/**
	 * Creates world 2
	 * @param world Reference to the world
	 */
	private static void world2(GameMap world)
	{	
		world.setWidth(1000);
		world.setHeight(600);
	
		// Obstacles
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 20, 20);
		world.addAnthill(EnumTeams.b, 980, 580);
		world.addAnthill(EnumTeams.c, 980, 20);
		world.addAnthill(EnumTeams.d, 20, 580);
		
		world.addMap("/img/world.png");
	}

	/**
	 * Creates world 3
	 * @param world Reference to the world
	 */
	private static void world3(GameMap world)
	{	
		world.setHeight(1500);
		world.setWidth(2000);

		// Obstacles
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 100, 100);
		world.addAnthill(EnumTeams.b, 1900, 1400);
		world.addAnthill(EnumTeams.c, 1900, 100);
		world.addAnthill(EnumTeams.d, 100, 1400);
		
		
		world.addWater(140, 140, 150);
		world.addWater(1860, 1360, 150);
		world.addWater(1860, 140, 150);
		world.addWater(140, 1360, 150);
	
		world.addFood(120, 120, 150);
		world.addFood(1880, 1380, 150);
		world.addFood(1880, 120, 150);
		world.addFood(120, 1380, 150);
		
		world.addMap("/img/world3.png");
	}
	
	/**
	 * Creates resource experiment world 1
	 * @param world Reference to teh world
	 */
	private static void worldResourceExperiment1(GameMap world)
	{
		world.setHeight(600);
		world.setWidth(1000);

		// Obstacles
		world.addObstacle(new Point(450,300), new Point(300,150), new Point(300,450));
		world.addObstacle(new Point(550,300), new Point(700,150), new Point(700,450));
		world.addObstacle(new Point(500,250), new Point(350,100), new Point(650,100));
		world.addObstacle(new Point(500,350), new Point(350,500), new Point(650,500));
		
		world.addObstacle(new Point(150,350), new Point(300,500), new Point(50,550));
		world.addObstacle(new Point(900,350), new Point(700,500), new Point(950,550));
		world.addObstacle(new Point(150,250), new Point(300,100), new Point(50,50));
		world.addObstacle(new Point(900,250), new Point(700,100), new Point(950,50));
		
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 500, 50);
		world.addAnthill(EnumTeams.b, 500, 550);
		world.addAnthill(EnumTeams.c, 200, 300);
		world.addAnthill(EnumTeams.d, 800, 300);

		// Water
		world.addWater(550, 350, 50);
		world.addWater(100, 300, 50);
		world.addWater(500, 100, 50);
		world.addWater(900, 550, 50);
		world.addWater(50, 200, 50);
	
		// Food
		world.addFood(450, 250, 50);
		world.addFood(900, 300, 50);
		world.addFood(500, 500, 50);
		world.addFood(850, 25, 50);
		world.addFood(240, 560, 50);
		
		world.addMap("/img/world.png");
	}
	
	/**
	 * Creates resource experiment world 2
	 * @param world Reference to the world
	 */
	private static void worldResourceExperiment2(GameMap world)
	{
		world.setHeight(1000);
		world.setWidth(1000);

		// Obstacles
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 500, 500);
		world.addAnthill(EnumTeams.b, 300, 700);
		world.addAnthill(EnumTeams.c, 900, 300);
		world.addAnthill(EnumTeams.d, 200, 800);

		// Water
		world.addWater(550, 50, 50);
		world.addWater(120, 840, 50);
		world.addWater(50, 124, 50);
		world.addWater(930, 510, 50);
		world.addWater(340, 290, 50);
	
		// Food
		world.addFood(431, 345, 50);
		world.addFood(341, 900, 50);
		world.addFood(598, 500, 50);
		world.addFood(964, 943, 50);
		world.addFood(220, 937, 50);
	}
	
	/**
	 * Creates resource experiment world 3
	 * @param world Reference to the world
	 */
	private static void worldResourceExperiment3(GameMap world)
	{
		world.setHeight(2000);
		world.setWidth(2000);

		// Obstacles
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 400, 400);
		world.addAnthill(EnumTeams.b, 300, 500);
		world.addAnthill(EnumTeams.c, 900, 500);
		world.addAnthill(EnumTeams.d, 500, 500);

		// Water
		world.addWater(50, 1750, 10);
		world.addWater(20, 540, 10);
		world.addWater(1050, 1424, 10);
		world.addWater(30, 352, 10);
		world.addWater(1040, 1890, 10);
		
		world.addWater(150, 1350, 10);
		world.addWater(1120, 840, 10);
		world.addWater(150, 1824, 10);
		world.addWater(1130, 1352, 10);
		world.addWater(140, 950, 10);

		world.addWater(250, 650, 10);
		world.addWater(220, 1340, 10);
		world.addWater(1250, 624, 10);
		world.addWater(1230, 1552, 10);
		world.addWater(1240, 990, 10);

		world.addWater(350, 250, 10);
		world.addWater(1320, 1440, 10);
		world.addWater(1300, 324, 10);
		world.addWater(430, 1452, 10);
		world.addWater(440, 890, 10);

		world.addWater(1550, 1950, 10);
		world.addWater(520, 940, 10);
		world.addWater(1550, 1324, 10);
		world.addWater(530, 952, 10);
		world.addWater(1540, 1590, 10);

		world.addWater(650, 650, 10);
		world.addWater(1620, 1940, 10);
		world.addWater(650, 124, 10);
		world.addWater(1630, 1352, 10);
		world.addWater(640, 590, 10);

		world.addWater(750, 1750, 10);
		world.addWater(1720, 1940, 10);
		world.addWater(1750, 24, 10);
		world.addWater(1730, 152, 10);
		world.addWater(740, 1290, 10);

		world.addWater(850, 550, 10);
		world.addWater(1820, 1940, 10);
		world.addWater(850, 1024, 10);
		world.addWater(1830, 352, 10);
		world.addWater(1840, 1790, 10);

		world.addWater(950, 250, 10);
		world.addWater(1920, 1840, 10);
		world.addWater(1950, 724, 10);
		world.addWater(930, 1352, 10);
		world.addWater(940, 990, 10);

		world.addWater(350, 1250, 10);
		world.addWater(1020, 840, 10);
		world.addWater(650, 1724, 10);
		world.addWater(1030, 352, 10);
		world.addWater(940, 1990, 10);
	
		// Food
		world.addFood(31, 45, 10);
		world.addFood(1041, 1280, 10);
		world.addFood(98, 230, 10);
		world.addFood(64, 443, 10);
		world.addFood(1020, 1937, 10);

		world.addFood(1131, 345, 10);
		world.addFood(141, 1980, 10);
		world.addFood(1198, 430, 10);
		world.addFood(164, 1643, 10);
		world.addFood(1120, 337, 10);

		world.addFood(231, 1045, 10);
		world.addFood(1241, 1880, 10);
		world.addFood(1298, 630, 10);
		world.addFood(1264, 1443, 10);
		world.addFood(220, 237, 10);

		world.addFood(1331, 945, 10);
		world.addFood(1341, 780, 10);
		world.addFood(1398, 1530, 10);
		world.addFood(364, 343, 10);
		world.addFood(320, 1137, 10);

		world.addFood(1431, 945, 10);
		world.addFood(1441, 1580, 10);
		world.addFood(498, 1430, 10);
		world.addFood(464, 543, 10);
		world.addFood(420, 1337, 10);

		world.addFood(531, 1945, 10);
		world.addFood(1541, 1580, 10);
		world.addFood(598, 830, 10);
		world.addFood(564, 1143, 10);
		world.addFood(1520, 137, 10);

		world.addFood(1631, 645, 10);
		world.addFood(641, 1080, 10);
		world.addFood(1698, 230, 10);
		world.addFood(1664, 1343, 10);
		world.addFood(620, 937, 10);

		world.addFood(731, 1045, 10);
		world.addFood(1741, 680, 10);
		world.addFood(1798, 1630, 10);
		world.addFood(764, 243, 10);
		world.addFood(1720, 1037, 10);

		world.addFood(1831, 345, 10);
		world.addFood(841, 780, 10);
		world.addFood(1898, 1630, 10);
		world.addFood(864, 1343, 10);
		world.addFood(1820, 937, 10);

		world.addFood(1931, 1545, 10);
		world.addFood(941, 480, 10);
		world.addFood(1988, 1630, 10);
		world.addFood(1964, 543, 10);
		world.addFood(920, 1137, 10);
	}
	
	/**
	 * Creates death match experiment's world
	 * @param world Reference to the world
	 */
	private static void worldDeathMatch(GameMap world)
	{	
		world.setWidth(1000);
		world.setHeight(600);
		
		// Ant hills
		world.addAnthill(EnumTeams.a, 100, 300);
		world.addAnthill(EnumTeams.b, 900, 300);
		world.addAnthill(EnumTeams.c, 500, 100);
		world.addAnthill(EnumTeams.d, 500, 500);
		
		// Obstacles
		world.addObstacle(new Point(200,280), new Point(80,150), new Point(400,50));
		world.addObstacle(new Point(200,320), new Point(80,450), new Point(400,550));
		world.addObstacle(new Point(800,280), new Point(920,150), new Point(600,50));
		world.addObstacle(new Point(800,320), new Point(920,450), new Point(600,550));
		
		world.addObstacle(new Point(450,150), new Point(400,300), new Point(450,450));
		world.addObstacle(new Point(550,150), new Point(600,300), new Point(550,450));

		// Water
		world.addWater(20, 20, 25);
		world.addWater(980, 20, 25);
		world.addWater(20, 280, 25);
		world.addWater(980, 280, 25);
		
		world.addWater(400, 580, 25);
		world.addWater(600, 580, 25);
		world.addWater(300, 300, 25);
		world.addWater(700, 300, 25);
		
		// Food
		world.addFood(20, 580, 25);
		world.addFood(980, 580, 25);
		world.addFood(20, 320, 25);
		world.addFood(980, 320, 25);
		
		world.addFood(400, 20, 25);
		world.addFood(600, 20, 25);
		world.addFood(250, 300, 25);
		world.addFood(750, 300, 25);
		
		world.addMap("/img/dm.png");
	}
	
	/**
	 * Creates performance experiment's world
	 * @param world Reference to the world
	 */
	private static void randomWorld(GameMap world)
	{
		world.setWidth(Model.getConfiguration().getWorldSize());
		world.setHeight(Model.getConfiguration().getWorldSize());
		

		world.addAnthill(EnumTeams.a, Model.getConfiguration().getWorldSize()/10, Model.getConfiguration().getWorldSize()/10);
		world.addAnthill(EnumTeams.b, 9*Model.getConfiguration().getWorldSize()/10, 9*Model.getConfiguration().getWorldSize()/10);
		world.addAnthill(EnumTeams.c, 9*Model.getConfiguration().getWorldSize()/10, Model.getConfiguration().getWorldSize()/10);
		world.addAnthill(EnumTeams.d, Model.getConfiguration().getWorldSize()/10, 9*Model.getConfiguration().getWorldSize()/10);
	}
}
