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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class T2ItSecurityPuzzleTest {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}

	@Test
	@Order(1)
	void checkRunCommands() {
		Module world = getModule(Tasks.task_2);
		assertEquals(1, world.getAllCommands().size(), "Number of run commands is not 1");
		Command c = world.getAllCommands().get(0);
		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (!ans.satisfiable()) {
			fail("No solution found");
		}
	}

	@Test
	@Order(2)
	void checkIrreflexiveSecurity() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact reflexiveSecurity {\n" +
				"	some x : Admin | x in x.higherSec \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("Relation higherSec is not irreflexive.");
		}
	}


	@Test
	@Order(3)
	// 3. No two admins have the same security level.
	void checkNoTwoAdminsSameSecurityLevel() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact noTwoAdminsSameSecurityLevel {\n" +
				"Alice.higherSec = Bob.higherSec or  \n" +
				"Bob.higherSec = Charlie.higherSec or  \n" +
				"Charlie.higherSec = Alice.higherSec \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("No two admins may have the same security level.");
		}
	}

	@Test
	@Order(4)
	// 5. Everybody trusts at least one admin.
	void checkEverybodyTrustsAtLeastOneAdmin() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact everybodyTrustsAtLeastOneAdmin {\n" +
				"no Alice.trusts or no Bob.trusts or no Charlie.trusts \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("Some admin trusts no admin.");
		}
	}

	@Test
	@Order(5)
	// 6. No admin trusts an admin with a lower security level.
	void checkNoAdminTrustsLowerSecurityLevel() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact noAdminTrustsLowerSecurityLevel {\n" +
				"some disj x,y : Admin | x in y.trusts and x not in y.higherSec \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("An admin trusts an admin with a lower security level.");
		}
	}

	@Test
	@Order(6)
	// 7. Alice trusts at most those who trusts her.
	void checkAliceTrustsAtMostThoseWhoTrustHer() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact aliceTrustsAtMostThoseWhoTrustHer {\n" +
				"some x : Admin | x in Alice.trusts and Alice not in x.trusts \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("Alice trusts someone who does not trust her.");
		}
	}

	@Test
	@Order(7)
  // 8. Bob trusts exactly those who have a higher security level than Charlie.
	void checkBobTrustsThoseHigherThanCharlie() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact bobTrustsThoseHigherThanCharlie {\n" +
				"some x : Admin | x in Bob.trusts and not x in Charlie.^higherSec \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);
		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("Bob does not trust exactly those who have a higher security level than Charlie.");
		}
	}
	
	@Test
	@Order(8)
	// 9. Charlie trusts no one who trusts Bob.
	void checkCharlieTrustsNoOneWhoTrustsBob() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact charlieTrustsNoOneWhoTrustsBob {\n" +
				"some x : Admin | x in Charlie.trusts and Bob in x.trusts \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);
		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("Charlie trusts someone who trusts Bob.");
		}
	}

	// 10. The bad actor trusts only those who do not trust Alice.
	@Test
	@Order(9)
	void checkBadActorTrustsOnlyThoseWhoDoNotTrustAlice() {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
		code += "\n\nfact badActorTrustsOnlyThoseWhoDoNotTrustAlice {\n" +
				"some x : Admin | x in BadActor.trusts and Alice in x.trusts \n" +
				"}\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		Command c = world.getAllCommands().get(0);
		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		if (ans.satisfiable()) {
			fail("The bad actor trusts someone who trusts Alice.");
		}
	}

	public Module getModule(String task) {

		String code = FmPlay.getCodeFromPermalink(task);

		Module world = CompUtil.parseEverything_fromString(rep, code);
		return world;
	}

}
