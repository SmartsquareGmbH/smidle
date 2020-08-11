import humanreadableLifetime from "../lifetimeCalculator"

test("returns humanreadable time", () => {
  expect(humanreadableLifetime(60, false)).toBe("1 hour")
})
