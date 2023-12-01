package de.buw.fm4se.alloy;

import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class Task2Test {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	void checkRunCommands () {
		Module world = getModule(Tasks.task_2);
		assertEquals(1, world.getAllCommands().size(), "Number of run commands is not 1");
		Command c = world.getAllCommands().get(0);
		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (!ans.satisfiable()) {
			fail("No solution found");
		}
	}

	@Test
	void checkKillerOfAgatha () {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact agathaKiller {\n" + 
				"	Agatha in Agatha.killed\n" + 
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (!ans.satisfiable()) {
			fail("Model does not identify the correct killer of Agatha");
		}		
	}

	@Test
	void checkNotKillerOfAgatha () {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact agathaKiller {\n" + 
				"	Agatha not in Agatha.killed\n" + 
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("Model does not identify the correct killer of Agatha");
		}		
	}

	public Module getModule(String task) {
		
		String code = FmPlay.getCodeFromPermalink(task);

		Module world = CompUtil.parseEverything_fromString(rep, code);
		return world;
	}

}
