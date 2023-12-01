package de.buw.fm4se.alloy;

import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.ast.Sig;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class Task1Test {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	void checkNumberOfSignatures() {
		Module world = getModule(Tasks.task_1);
		assertTrue(world.getAllSigs().size() >= 4, "Number of signatures is less than 4");
	}

	@Test
	void checkNumberOfFieldsPerSignature() {
		Module world = getModule(Tasks.task_1);
		for (Sig s : world.getAllSigs()) {
			assertTrue(s.getFields().size() >= 2, "Number of fields is less than 2 in signature " + s.label);
		}
	}

	@Test
	void checkInheritance() {
		Module world = getModule(Tasks.task_1);
		for (Sig s : world.getAllSigs()) {
			if (!s.isTopLevel()) {
				return;
			}
		}
		fail("No signature found that extebnds another signature.");
	}

	@Test
	void checkNumberOfFacts() {
		Module world = getModule(Tasks.task_1);
		assertTrue(world.getAllFacts().size() >= 2, "Number of facts is less than 2");
	}

	@Test
	void checkNumberOfPredicates() {
		Module world = getModule(Tasks.task_1);
		assertTrue(world.getAllFunc().size() >= 2, "Number of predicates is less than 2");
	}

	@Test
	void checkNumberOfRunCommands () {
		Module world = getModule(Tasks.task_1);
		assertTrue(world.getAllCommands().size() >= 2, "Number of run commands is less than 2");		
	}

	@Test
	void checkRunCommands () {
		boolean foundSat = false;
		boolean foundSatTwoInst = false;

		boolean foundUnsat = false;
		Module world = getModule(Tasks.task_1);
		for (Command c : world.getAllCommands()) {			
			// run command
			A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
			if (ans.satisfiable()) {
				if (ans.next().satisfiable()) {
					foundSatTwoInst = true;
				}
				foundSat = true;
			} else {
				foundUnsat = true;
			}
		}
		assertTrue(foundSat, "No satisfiable run command found");
		assertTrue(foundUnsat, "No unsatisfiable run command found");
		assertTrue(foundSatTwoInst, "No satisfiable run command with two instances found");
	}


	public Module getModule(String task) {
		
		String code;
		code = FmPlay.getCodeFromPermalink(Tasks.task_1);

		Module world = CompUtil.parseEverything_fromString(rep, code);
		return world;
	}

}
