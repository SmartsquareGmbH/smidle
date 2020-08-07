import humanreadableLifetime from "../lifetimeCalculator"

test("returns humanreadable time", () => {
  expect(humanreadableLifetime(60)).toBe("1 hour")
})
